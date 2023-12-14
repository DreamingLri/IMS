package com.example.imsbackend.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.imsbackend.entity.CourseTime;
import com.example.imsbackend.entity.Courses;
import com.example.imsbackend.entity.UserCourse;
import com.example.imsbackend.entity.dto.InsertCourseDTO;
import com.example.imsbackend.entity.vo.CourseVO;
import com.example.imsbackend.entity.vo.UserCourseVO;
import com.example.imsbackend.mapper.struct.BeanCopyUtil;
import com.example.imsbackend.service.CourseTimeService;
import com.example.imsbackend.service.CoursesService;
import com.example.imsbackend.service.UserCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CoursesService coursesService;
    private final UserCourseService userCourseService;
    private final CourseTimeService courseTimeService;

    @GetMapping("/listCourse")
    public List<Courses> listCourse(String name){
        return coursesService.listCourse(name);
    }

    @GetMapping("/listCourseWithTime")
    public List<JSONObject> listCourseWithTime(String name){
        List<Courses> courses = coursesService.listCourse(name);
        List<JSONObject> list = CollUtil.newArrayList();
        for(Courses course : courses){
            List<CourseTime> courseTimes = courseTimeService.getCourseTimeById(course.getId());
            if(!ObjectUtil.isEmpty(courseTimes)){
                for(CourseTime courseTime : courseTimes){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", courseTime.getId());
                    jsonObject.put("course", course);
                    jsonObject.put("weekday", courseTime.getWeekday());
                    jsonObject.put("session", courseTime.getSession());
                    list.add(jsonObject);
                }
            }
        }
        return list;
    }

    @GetMapping("listCourseWithTimeByUserId")
    public List<JSONObject> listCourseWithTimeByUserId(Integer userId){
        List<Courses> courses = coursesService.listCourseByUserId(userId);
        List<JSONObject> list = new ArrayList<>();
        for(Courses course : courses){
            List<CourseTime> courseTimes = courseTimeService.getCourseTimeById(course.getId());
            if(!ObjectUtil.isEmpty(courseTimes)){
                for(CourseTime courseTime : courseTimes){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", courseTime.getId());
                    jsonObject.put("course", course);
                    jsonObject.put("weekday", courseTime.getWeekday());
                    jsonObject.put("session", courseTime.getSession());
                    list.add(jsonObject);
                }
            }
        }
        return list;
    }

    @PostMapping("/insertCourse")
    public boolean insertCourse(@Valid @RequestBody Courses courses){
        return coursesService.insertCourse(courses);
    }

    //根据UserId新建课程(带自动选课,自动创建考试)
    @PostMapping("insertCourseByUserId")
    public boolean insertCourseByUserId(@RequestBody InsertCourseDTO coursesDTO){
        int userId = coursesDTO.getUserId();
        Courses courses = BeanCopyUtil.INSTANCE.toCourses(coursesDTO);
        coursesService.insertCourse(courses);
        userCourseService.selectCourse(new UserCourse(userId, courses.getId()));

        return true;
    }

    @PostMapping("/updateCourseById")
    public boolean updateCourseById(@Valid @RequestBody Courses courses){
        return coursesService.updateCourseById(courses);
    }

    @PostMapping("/updateCourseTimeById")
    public boolean updateCourseTimeById(@Valid @RequestBody CourseTime courseTime){
        return courseTimeService.updateCourseTimeById(courseTime);
    }

    @DeleteMapping("/deleteCourseTimeById/{id}")
    public boolean deleteCourseTimeById(@PathVariable Integer id){
        return courseTimeService.deleteCourseTimeById(id);
    }

    @PostMapping("/insertCourseTime")
    public boolean insertCourseTime(@RequestBody CourseTime courseTime){
        return courseTimeService.insertCourseTime(courseTime);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public boolean deleteCourseById(@PathVariable Integer id){
        return coursesService.deleteCourseById(id);
    }

    //查询是否选课
    @GetMapping("/listCourseById")
    public List<CourseVO> getCourseId(int id){
        return coursesService.selectCourseById(id);
    }

    //跟据UserId展示已选课程
    @GetMapping("/listCourseByUserId")
    public List<Courses> listCourseByUserId(Integer userId){
        return coursesService.listCourseByUserId(userId);
    }

    //选课人数接口
    @GetMapping("/listNumberOfStudentSelectCourse")
    public List<UserCourseVO> listNumberOfStudentSelectCourse(){
        return userCourseService.listNumberOfStudentSelectCourse();
    }

    @GetMapping("/listNumberOfStudentSelectCourseByUserId")
    public List<UserCourseVO> listNumberOfStudentSelectCourseByUserId(Integer userId){
        return userCourseService.listNumberOfStudentSelectCourseByUserId(userId);
    }

    //退选课接口
    @PostMapping("/selectCourse")
    public boolean selectCourse(@RequestBody UserCourse userCourse){
        return userCourseService.selectCourse(userCourse);
    }

    @PostMapping("/withdrawCourse")
    public boolean withdrawCourse(@RequestBody UserCourse userCourse){
        return userCourseService.withdrawCourse(userCourse);
    }

    //课程表接口
    @GetMapping("/getCourseTimeById")
    public List<CourseTime> getCourseTimeById(int id){
        return courseTimeService.getCourseTimeById(id);
    }

    @GetMapping("/getCourseTable/{id}")
    public List<JSONObject> getCourseTable(@PathVariable Integer id){
        List<CourseTime> courseTimes = courseTimeService.getCourseTimeByUserId(id);

        List<JSONObject> list = CollUtil.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        JSONObject jsonObject5 = new JSONObject();
        JSONObject jsonObject6 = new JSONObject();
        JSONObject jsonObject7 = new JSONObject();
        JSONObject jsonObject8 = new JSONObject();
        JSONObject jsonObject9 = new JSONObject();
        JSONObject jsonObject10 = new JSONObject();
        JSONObject jsonObject11 = new JSONObject();
        list.add(jsonObject1);
        list.add(jsonObject2);
        list.add(jsonObject3);
        list.add(jsonObject4);
        list.add(jsonObject5);
        list.add(jsonObject6);
        list.add(jsonObject7);
        list.add(jsonObject8);
        list.add(jsonObject9);
        list.add(jsonObject10);
        list.add(jsonObject11);
        jsonObject1.put("session", JSONUtil.parseObj("{\"num\": \"第一节\", \"time\": \"8:00~8：45\"}"));
        jsonObject2.put("session", JSONUtil.parseObj("{\"num\": \"第二节\", \"time\": \"8:55~9:40\"}"));
        jsonObject3.put("session", JSONUtil.parseObj("{\"num\": \"第三节\", \"time\": \"10:10~10:55\"}"));
        jsonObject4.put("session", JSONUtil.parseObj("{\"num\": \"第四节\", \"time\": \"11:05~11:50\"}"));
        jsonObject5.put("session", JSONUtil.parseObj("{\"num\": \"第五节\", \"time\": \"14:20~15:05\"}"));
        jsonObject6.put("session", JSONUtil.parseObj("{\"num\": \"第六节\", \"time\": \"15:15~16:00\"}"));
        jsonObject7.put("session", JSONUtil.parseObj("{\"num\": \"第七节\", \"time\": \"16:20~17:05\"}"));
        jsonObject8.put("session", JSONUtil.parseObj("{\"num\": \"第八节\", \"time\": \"17:15~18:00\"}"));
        jsonObject9.put("session", JSONUtil.parseObj("{\"num\": \"第九节\", \"time\": \"19:00~19:45\"}"));
        jsonObject10.put("session", JSONUtil.parseObj("{\"num\": \"第十节\", \"time\": \"19:55~20:40\"}"));
        jsonObject11.put("session", JSONUtil.parseObj("{\"num\": \"第十一节\", \"time\": \"20:50~21:35\"}"));

        courseTimes.forEach(courseTime -> {
            Integer CourseId = courseTime.getCourseId();
            String weekday = courseTime.getWeekday();
            String session = courseTime.getSession();
            Courses course = coursesService.listCourseById(CourseId);
            switch (session){
                case "一":
                    setJson(weekday, course, jsonObject1);break;
                case "二":
                    setJson(weekday, course, jsonObject2);break;
                case "三":
                    setJson(weekday, course, jsonObject3);break;
                case "四":
                    setJson(weekday, course, jsonObject4);break;
                case "五":
                    setJson(weekday, course, jsonObject5);break;
                case "六":
                    setJson(weekday, course, jsonObject6);break;
                case "七":
                    setJson(weekday, course, jsonObject7);break;
                case "八":
                    setJson(weekday, course, jsonObject8);break;
                case "九":
                    setJson(weekday, course, jsonObject9);break;
                case "十":
                    setJson(weekday, course, jsonObject10);break;
                case "十一":
                    setJson(weekday, course, jsonObject11);break;
            }
        });
        return list;
    }

    private void setJson(String weekday, Courses course, JSONObject jsonObject){
        if("星期一".equals(weekday))
            jsonObject.put("mon", course);
        else if("星期二".equals(weekday))
            jsonObject.put("tue", course);
        else if("星期三".equals(weekday))
            jsonObject.put("wed", course);
        else if("星期四".equals(weekday))
            jsonObject.put("thu", course);
        else if("星期五".equals(weekday))
            jsonObject.put("fri", course);
        else if("星期六".equals(weekday))
            jsonObject.put("sat", course);
        else if("星期日".equals(weekday))
            jsonObject.put("sun", course);
    }
}
