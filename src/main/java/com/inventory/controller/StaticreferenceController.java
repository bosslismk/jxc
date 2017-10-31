package com.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticreferenceController {


    @RequestMapping("/Staticreference/include-css")
    public String includecss(){
        return "jsp/common/include-css";
    }
    @RequestMapping("/Sraticreference/header")
    public String header(){
        return "jsp/common/header";
    }
    @RequestMapping("/Sreticreference/user-panel")
    public String userpanel(){
        return "jsp/common/user-panel";
    }
    @RequestMapping("/Sreticreference/tree")
    public String tree(){
        return "jsp/common/tree";
    }
    @RequestMapping("/Sreticeference/footer")
    public String footer(){
        return "jsp/common/footer";
    }
    @RequestMapping("/Sreticeference/includejs")
    public String includejs(){
        return "jsp/common/include-js";
    }
    @RequestMapping("/Sreticeference/index")
    public String index(){
        return "jsp/index";
    }


    @RequestMapping("/Sreticeference/customer/list")
    public String customerlist(){
        return "jsp/customer/list";
    }
    @RequestMapping("/Sreticeference/customer/edit")
    public String customeredit(){
        return "jsp/customer/edit";
    }

    @RequestMapping("/Sreticeference/department/list")
    public String departmentlist(){
        return "jsp/department/list";
    }
    @RequestMapping("/Sreticeference/department/edit")
    public  String departmentedit(){
        return "jsp/department/edit";
    }

    @RequestMapping("/Sreticeference/employee/list")
    public String employeelist()
    {
        return "jsp/employee/list";
    }
    @RequestMapping("/Sreticeference/employee/edit")
    public String employeeedit(){
        return "jsp/employee/edit";
    }

    @RequestMapping("/Sreticeference/order/list")
    public  String orderlist(){
        return "jsp/order/list";
    }
    @RequestMapping("/Sreticeference/order/edit")
    public String orderedit(){
        return "jsp/order/edit";
    }
    @RequestMapping("/Sreticeference/order/descr")
    public String orderdescr(){
        return "jsp/order/descr";
    }

    @RequestMapping("/Sreticeference/outstore/list")
    public String outstorelist(){
        return "jsp/outstore/list";
    }
    @RequestMapping("/Sreticeference/outstore/edit")
    public String outstoreedit(){
        return "jsp/outstore/edit";
    }
    @RequestMapping("/Sreticeference/outstore/descr")
    public String outstoredescr(){
        return "jsp/outstore/descr";
    }


    @RequestMapping("/Sreticeference/productcategory/list")
    public String productcategorylist(){
        return "jsp/productcategory/list";
    }
    @RequestMapping("/Sreticeference/productcategory/edit")
    public String productcategoryedit(){
        return "jsp/productcategory/edit";
    }

    @RequestMapping("/Sreticeference/product/list")
    public String productlist(){
        return "jsp/product/list";
    }
    @RequestMapping("/Sreticeference/product/edit")
    public String productedit(){
        return "jsp/product/edit";
    }
    @RequestMapping("/Sreticeference/product/stores")
    public String productstores(){
        return "jsp/product/stores";
    }


    @RequestMapping("/Sreticeference/receipt/list")
    public String receiptlist(){
        return "jsp/receipt/list";
    }
    @RequestMapping("/Sreticeference/receipt/edit")
    public String receiptedit(){
        return "jsp/receipt/edit";
    }
    @RequestMapping("/Sreticeference/receipt/addItem")
    public String receiptaddItem(){
        return "jsp/receipt/addItem";
    }
    @RequestMapping("/Sreticeference/receipt/descr")
    public String receiptdescr(){
        return "jsp/receipt/descr";
    }

    @RequestMapping("/Sreticeference/store/list")
    public String storelist(){
        return "jsp/store/list";
    }
    @RequestMapping("/Sreticeference/store/edit")
    public String storeedit(){
        return "jsp/store/edit";
    }

}
