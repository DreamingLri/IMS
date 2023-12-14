<script setup>

import {
  House,
  User,
  Notebook,
  EditPen,
  Position, Collection,
} from "@element-plus/icons-vue";
import router from "@/router";

import { useInfoStore } from "@/stores/pinna";
const userInfo = useInfoStore()

function logout(){
  localStorage.clear()
  router.push('/')
}
</script>

<template>
  <div class="common-layout">
    <el-container style="height: 100vh">
      <el-container>
        <el-header class="el-header">
          <div class="header-wrapper">
            <div>
              <el-dropdown>
                <el-avatar class="el-avatar"
                           src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                />
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="router.push('/teacher/dashboard')">个人中心</el-dropdown-item>
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
  text-align: right;
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