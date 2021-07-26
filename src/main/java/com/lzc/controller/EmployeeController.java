package com.lzc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzc.bean.Employee;
import com.lzc.bean.Msg;
import com.lzc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /*
        查询员工数据(分页查询)
     */
    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue ="1")Integer pn, Model model){
        PageHelper.startPage(pn,4);
        PageHelper.orderBy("emp_id asc");
        List<Employee> emps = employeeService.getAll();
        PageInfo pageInfo = new PageInfo(emps,2);
        System.out.println(pageInfo);
        return Msg.success().add("pageInfo",pageInfo);
    }

    /*
    查询员工数据(单个查询)
 */
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /*
    新增员工
     */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            //数据校验失败,模态框显示信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error:errors) {
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("error",map);
        }else{
        employeeService.saveEmp(employee);
        return Msg.success();
        }
    }
    /*
    删除员工
     */
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmp(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            //批量删除
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            for (String string:str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
            return Msg.success();
        }else{
            //单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
            return Msg.success();
        }
    }
    /*
       修改员工
        */
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee){
        employeeService.updateEmp(employee);
        return Msg.success();

    }

    /*
    检查用户名是否可用
     */
    @RequestMapping("/checkEmp")
    @ResponseBody
    public Msg checkEmp(String empName){
        String regName = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\u4E00-\u9FA5]{2,6})";
        if (!empName.matches(regName)){
            return Msg.fail().add("check_msg","用户名可以是4-16位英文,或者2-6位中文(后端校验)");
        }
        boolean b = employeeService.checkRepeat(empName);
        if(b){
            return Msg.success();
        }else {
            return Msg.fail().add("check_msg","用户名不可用(后端校验)");
        }
    }


    /*
        查询员工数据(分页查询)----------
     */
   // @RequestMapping(value = "/emp")
    public String getEmps(@RequestParam(value = "pn",defaultValue ="1")Integer pn, Model model){
        PageHelper.startPage(pn,4);
        PageHelper.orderBy("emp_id asc");
        List<Employee> emps = employeeService.getAll();
        PageInfo pageInfo = new PageInfo(emps,3);
        model.addAttribute("pageInfo",pageInfo);

//        System.out.println("当前页码:"+pageInfo.getPageNum());
//        System.out.println("总页码:"+pageInfo.getPages());
//        System.out.println("总记录数:"+pageInfo.getTotal());
//        System.out.println("在页面需要连续显示的页码:");
//        int [] nums = pageInfo.getNavigatepageNums();
//        for (int i:nums) {
//            System.out.print(" "+i);
//        }
//        List<Employee> list = pageInfo.getList();
//        for (Employee employee:list) {
//            System.out.println("ID:"+employee.getEmpId()+"-->Name:"+employee.getEmpName());
//        }
        return "list";
    }
}
