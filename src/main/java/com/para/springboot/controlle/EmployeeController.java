package com.para.springboot.controlle;

import com.para.springboot.dao.DepartmentDao;
import com.para.springboot.dao.EmployeeDao;
import com.para.springboot.entities.Department;
import com.para.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * @Author: YixinZhang
 * @Date: Created in 10:17 2020/1/9
 * @Description:
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    /**
     * 查询所有员工，返回列表页面
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model){
        Collection<Employee> all = employeeDao.getAll();
        model.addAttribute("emps",all);
        return "emp/list";
    }

    /**
     * 添加员工页面
     * @return
     */
    @GetMapping("/emp")
    public String toAddEmpPage(Model model){
        /*查询出所有部门，*/
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    /**
     * 添加员工功能
     * @return
     */
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("----->"+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 查询出指定id的员工，并进行回显
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){

        Employee employee = employeeDao.get(id);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        model.addAttribute("emp",employee);

        //回到编辑页面
        return "emp/add";

    }
}
