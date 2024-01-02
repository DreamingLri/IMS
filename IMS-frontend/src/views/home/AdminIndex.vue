<script setup>

import {
  House,
  User,
  Notebook,
  EditPen,
  Position, Collection, Files, School,
} from "@element-plus/icons-vue";
import router from "@/router";

import { useInfoStore } from "@/stores/pinna";
import {ref} from "vue";
let user = JSON.parse(localStorage.getItem("user"))
const sideBarView = ref(true)

function logout(){
  router.push('/')
}
</script>

<template>
  <div class="common-layout">
    <el-container style="height: 100vh">
      <el-aside width="200px" style="border-right: 1px solid #ccc">
        <div style="height: 55px; display: flex; justify-content: center; align-items: center">
          后台管理系统
          <el-icon style="margin-left: 10px"><Position /></el-icon>
        </div>
        <div>
          <el-scrollbar class="el-scrollbar">
            <el-menu
                default-active="0"
                class="el-menu-vertical-demo"
            >
              <el-menu-item index="0" @click="router.push('/admin/dashboard')">
                <template #title>
                  <el-icon><House /></el-icon>
                  <span>首页</span>
                </template>
              </el-menu-item>
              <el-menu-item index="1" @click="router.push('/admin/all-user')">
                <template #title>
                  <el-icon><Files /></el-icon>
                  <span>用户权限总览</span>
                </template>
              </el-menu-item>
              <el-menu-item index="2" @click="router.push('/admin/school')">
                <template #title>
                  <el-icon><School /></el-icon>
                  <span>学院管理</span>
                </template>
              </el-menu-item>
              <el-sub-menu index="3">
                <template #title>
                  <el-icon><User /></el-icon>
                  <span>学生管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="3-1" @click="router.push('/admin/student-info')">学生信息</el-menu-item>
                  <el-menu-item index="3-2" @click="router.push('/admin/select-course')">学生选课</el-menu-item>
                  <el-menu-item index="3-3" @click="router.push('/admin/student-score')">学生成绩</el-menu-item>
                  <el-menu-item index="3-4" @click="router.push('/admin/class-table')">学生课表</el-menu-item>
                  <el-menu-item index="3-5" @click="router.push('/admin/accept-leave')">请假审批</el-menu-item>
                </el-menu-item-group>
              </el-sub-menu>
              <el-sub-menu index="4">
                <template #title>
                  <el-icon><Notebook /></el-icon>
                  <span>教师管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="4-1" @click="router.push('/admin/teacher-info')">教师信息</el-menu-item>
                  <el-menu-item index="4-2" @click="router.push('/admin/teacher-score')">教师评教</el-menu-item>
                  <el-menu-item index="4-5" @click="router.push('/admin/accept-leave')">请假审批</el-menu-item>
                </el-menu-item-group>
              </el-sub-menu>
              <el-sub-menu index="5">
                <template #title>
                  <el-icon><EditPen /></el-icon>
                  <span>考试管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="5-1" @click="router.push('/admin/exam')">考试信息</el-menu-item>
                  <el-menu-item index="5-2" @click="router.push('/admin/exam-student')">考试学生</el-menu-item>
                </el-menu-item-group>
              </el-sub-menu>
              <el-sub-menu index="6">
                <template #title>
                  <el-icon><Collection /></el-icon>
                  <span>课程管理</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item index="6-1" @click="router.push('/admin/course')">课程信息</el-menu-item>
                  <el-menu-item index="6-2" @click="router.push('/admin/course-time')">课程时间</el-menu-item>
                  <el-menu-item index="6-3" @click="router.push('/admin/course-status')">选课情况</el-menu-item>
                </el-menu-item-group>
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>
        </div>
      </el-aside>
      <el-container>
        <el-header class="el-header">
          <div class="header-wrapper">
            <div style="margin-top: 20px; margin-right: 20px">
              <el-text>{{user.username}} {{user.code}}，你好</el-text>
            </div>
            <div style="margin-top: 13px; margin-right: 20px">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div v-if="sideBarView">关闭侧边栏</div>
                  <div v-if="!sideBarView">显示侧边栏</div>
                </template>
                <template #reference>
                  <el-switch v-model="sideBarView" />
                </template>
              </el-popover>
            </div>
            <div>
              <el-dropdown>
              <el-avatar class="el-avatar"
                         src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="router.push('/admin/dashboard')">个人中心</el-dropdown-item>
                  <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
            </div>

          </div>
        </el-header>
        <el-main class="el-main">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.el-header{
  height: 55px;
}
.el-main{
  background-color: rgba(246, 249, 248);
  border-bottom: 1px solid #ccc;
}
.header-wrapper{
  width: 100%;
  display: flex;
  justify-content: flex-end;
}

.el-avatar{
  width: 35px;
  height: 35px;
  margin-top: 10px;
}
.el-scrollbar{
  width: 100%;
  height: calc(100vh - 55px);
}
.el-scrollbar :deep(.el-menu){
  border-right: 0;
}
</style>