package com.ujiuye.search.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.ujiuye.dao.TbItemMapper;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.feign.ItemFeign;
import com.ujiuye.pojo.TbItemCat;
import com.ujiuye.search.dao.SkuEsMapper;
import com.ujiuye.search.pojo.SkuInfo;
import com.ujiuye.search.pojo.TbItem;
import com.ujiuye.search.service.SkuService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SkuServiecImpl implements SkuService {

    @Autowired
    ItemFeign itemFeign;
    @Autowired
    SkuEsMapper skuEsMapper;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void importSku() {
        Result itemFeignByStatus = itemFeign.findByStatus("1");
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(itemFeignByStatus.getData()), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfos) {
            skuInfo.setSpecMap(JSON.parseObject(skuInfo.getSpec()));
        }
        skuEsMapper.saveAll(skuInfos);
    }

    @Override
    public void delete() {
        skuEsMapper.deleteAll();
    }

    @Override
    public Map search(Map searchMap) {
        //1.获取关键字的值
        String keywords = (String)searchMap.get("keywords");

        //条件查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isEmpty((String)searchMap.get("brand")))
            boolQueryBuilder.filter(QueryBuilders.matchQuery("brand",searchMap.get("brand")));
        if (StringUtils.isEmpty((String)searchMap.get("category")))
            boolQueryBuilder.filter(QueryBuilders.matchQuery("category",searchMap.get("category")));

        if (StringUtils.isEmpty(keywords)) {
            keywords = "华为";//赋值给一个默认的值
        }
        //2.创建查询对象 的构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // ****** 设置分组的条件  terms后表示分组查询后的列名
        nativeSearchQueryBuilder.addAggregation(  AggregationBuilders.terms("skuCategorygroup").field("category")   );
        //设置分组查询,品牌
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrandgroup").field("brand"));
        //设置分组查询,规格
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(100));
        //3.设置查询的条件
        //使用：QueryBuilders.matchQuery("title", keywords) ，搜索华为 ---> 华    为 二字可以拆分查询，
        //使用：QueryBuilders.matchPhraseQuery("title", keywords) 华为二字不拆分查询
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title", keywords));
        //过滤条件
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);

        //4.构建查询对象
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        //5.执行查询
        SearchHits<SkuInfo> searchHits = elasticsearchRestTemplate.search(query, SkuInfo.class);
        //对搜索searchHits集合进行分页封装
        SearchPage<SkuInfo> skuPage = SearchHitSupport.searchPageFor(searchHits, query.getPageable());



        //******** 获取分组结果
        Terms cateTerms = searchHits.getAggregations().get("skuCategorygroup");
        Terms brandTerms = searchHits.getAggregations().get("skuBrandgroup");
        Terms specTerms = searchHits.getAggregations().get("skuSpecgroup");

        List<String> categoryList = this.getStringList(cateTerms);
        List<String> brandList = this.getStringList(brandTerms);
        Map<String, Set<String>> specMap = this.getStringMap(specTerms);


        //遍历取出查询的商品信息
        List<SkuInfo> skuList=new ArrayList<>();
        for (SearchHit<SkuInfo> searchHit :skuPage.getContent()) { // 获取搜索到的数据
            SkuInfo content = (SkuInfo) searchHit.getContent();
            skuList.add(content);
        }

        //6.返回结果
        Map resultMap = new HashMap<>();

        resultMap.put("rows", skuList);//获取所需SkuInfo集合数据内容
        resultMap.put("total",searchHits.getTotalHits());//总记录数
        resultMap.put("totalPages", skuPage.getTotalPages());//总页数

//****
        resultMap.put("categoryList",categoryList);
        resultMap.put("brandList",brandList);
        resultMap.put("specMap",specMap);

        return resultMap;
    }

    private List<String> getStringList(Terms terms){
        List<String> list = new ArrayList<>();
        if (terms != null) {
            for (Terms.Bucket bucket : terms.getBuckets()) {
                list.add(bucket.getKeyAsString());
            }
        }
        return  list;
    }

    private Map<String, Set<String>> getStringMap(Terms specTerms){
        Map<String ,Set<String>> specMap = new HashMap<>();
        //获取不重复的spec
        Set<String> specSet= new HashSet<>();
        if (specTerms!=null){
            for (Terms.Bucket bucket : specTerms.getBuckets()) {
                specSet.add(bucket.getKeyAsString());
            }
        }

        if (CollectionUtils.isEmpty(specSet))
            throw new RuntimeException("set is empty");
        
        for (String spec : specSet) {
            Map<String ,String> map = JSON.parseObject(spec, Map.class);
            for (String key : map.keySet()) {
                Set<String> values = specMap.get(key);
                if (values==null){
                    values = new HashSet<>();
                }
                values.add(map.get(key));
                specMap.put(key,values);
            }
        }

       return   specMap;
    }
}
