package com.ujiuye.itempage.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.itempage.service.PageService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiModel(description = "cotroller")
@RestController
@RequestMapping("/page")
public class ItemPageController {
    

    @Autowired
    PageService pageService;


    @RequestMapping("/createHtml/{id}")
    public Result cratePage(@PathVariable("id") long id){
        try {
            System.out.println("=======");
            pageService.createPageHtml(id);
            return new Result(true,20000,"创建成功",null);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"创建失败",null);
        }

    }

    @RequestMapping("/deleteHtml/{id}")
    public Result deletePage(@PathVariable long id){
        try {

            pageService.deletePageHtml(id);
            return new Result(true,20000,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"删除失败"+e.getMessage(),null);
        }

    }

    public static void main(String[] args) {
//        System.out.println(ItemPageController.addTwoNumbers(new ListNode(243), new ListNode(564)));
        System.out.println(reverse(1534236469));

    }

    public static int reverse(int x) {
        long res = 0;
        int res1 = 0;
        if(x>=0 && x <= Integer.MAX_VALUE  ){
            res = Long.parseLong(new StringBuilder(x+"").reverse().toString());
            if (res > Integer.MAX_VALUE)
                res1 = 0;
            else
                res1 = (int)res;
        }else if (x >= Integer.MIN_VALUE && x<0){
            String temp = x+"";
            res = Long.parseLong("-"+new StringBuilder(temp.replace("-","")).reverse().toString());
            if (res < Integer.MIN_VALUE)
                res1 = 0;
            else
                res1 = (int)res;
        }
        return res1;
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        String s = l1.val + "";
        String s1 = l2.val + "";
        if (s == "" || s1 == "")
            return null;
        int i1 = Integer.parseInt(new StringBuilder(s).reverse().toString());
        int i2 = Integer.parseInt(new StringBuilder(s1).reverse().toString());
        int res = i1+i2;
        int i3 = Integer.parseInt(new StringBuilder(res).reverse().toString());
        return new ListNode(i3);
    }
    public static class ListNode {
//        Executors .new
         int val;
         ListNode next;
         ListNode() {};
         ListNode(int val) { this.val = val; };
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
