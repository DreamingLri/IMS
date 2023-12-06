<script setup>
import {onMounted, reactive, ref} from "vue";
import { useInfoStore } from "@/stores/pinna";
import {ElMessage} from "element-plus";

const userInfo = useInfoStore()
let user = ref({
})
onMounted(()=>{
  user = userInfo.user
  console.log(user)
  ElMessage.success(userInfo.user.username + "，欢迎回来！")
})
</script>

<template>
  <div class="main-wrapper">
    <!--第一行-->
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="panel">
          <el-text style="font-size: 20px" size="large" type="primary">常用服务</el-text>
          <el-divider/>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="panel">
          <el-text style="font-size: 20px" size="large" type="primary">学分情况</el-text>
          <el-divider/>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="panel">
          <el-text style="font-size: 20px" size="large" type="primary">学业预警</el-text>
          <el-divider/>
        </div>
      </el-col>
    </el-row>
    <!--第二行-->
    <el-row :gutter="20">
      <el-col :span="24">
        <div class="class_panel">
          <el-text style="font-size: 20px" size="large" type="primary">课程表</el-text>
          <el-divider/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.main-wrapper{
  width: 100%;
  height: 100%;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 20px;
}
.panel{
  height: 300px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}

.class_panel{
  height: 600px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>