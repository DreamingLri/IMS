<script setup>
import {onMounted, reactive, ref} from "vue";
import { useInfoStore } from "@/stores/pinna";
import {ElMessage} from "element-plus";
import CreditChart from "@/views/echarts/credit-chart.vue";
import {
  Bell,
  Calendar,
  ChatLineSquare, CoffeeCup,
  Collection,
  DataAnalysis, DataBoard,
  MapLocation,
  PieChart,
  School
} from "@element-plus/icons-vue";
import GradePointsChart from "@/views/echarts/grade-points-chart.vue";
import ClassTable from "@/views/components/ClassTable.vue";
import router from "@/router"

const userInfo = useInfoStore()
let user = ref({})
onMounted(()=>{
  user = userInfo.user
  console.log(user)
  ElMessage.success(userInfo.user.username + "，欢迎回来！")
})

//日历
const calendar = ref()
const selectDate = (val) => {
  if (!calendar.value) return
  calendar.value.selectDate(val)
}

</script>

<template>
  <div class="main-wrapper">
    <!--第一行-->
    <el-row :gutter="20">
      <!--系统收藏夹-->
      <el-col :span="24">
        <el-card class="system-entrance" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><MapLocation /></el-icon>系统收藏夹</el-text></span>
            </div>
          </template>
          <div>
            <el-row :gutter="12">
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click="router.push('/student/select-course')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><Collection /></el-icon>
                    <p style="font-size: 15px">选课</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click="router.push('/student/teacher-score')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><ChatLineSquare /></el-icon>
                    <p style="font-size: 15px">我的评教</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><DataAnalysis /></el-icon>
                    <p style="font-size: 15px">我的成绩</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click="router.push('/student/class-table')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><DataBoard /></el-icon>
                    <p style="font-size: 15px">课表查询</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" @click="router.push('/student/leave')"><CoffeeCup /></el-icon>
                    <p style="font-size: 15px">请假申请</p>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第二行-->
    <el-row :gutter="20">

      <!--基本信息-->
      <el-col :span="8">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><User /></el-icon>学业预警</el-text></span>
            </div>
          </template>
          <div>

          </div>
        </el-card>
      </el-col>

      <!--学分-->
      <el-col :span="6">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><DataAnalysis /></el-icon>学分情况</el-text></span>
            </div>
          </template>
          <div style="transform: scale(0.7);">
            <credit-chart style="height: 10px"/>
          </div>
        </el-card>
      </el-col>

      <!--学院公告-->
      <el-col :span="10">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><Bell /></el-icon>学校公告</el-text></span>
            </div>
          </template>
          <div style="height: 240px">
            <el-carousel :interval="5000" arrow="always" height="auto" autoplay>
              <el-carousel-item style="height: 240px">
                <img src="../../../public/14th.jpg" alt="14th" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../public/2023cun-sysu.jpg" alt="2023cun-sysu" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../public/shouyezhuantitu.jpg" alt="shouyezhuantitu" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../public/zt2023xjp-sysu.jpg" alt="zt2023xjp-sysu" width="600">
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第二行-->
    <el-row :gutter="20">
      <!--日历-->
      <el-col :span="10">
        <el-card class="second_panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><Calendar /></el-icon>日历</el-text></span>
            </div>
          </template>
          <div style="transform: scale(0.8); height: 0; width: 100%">
            <el-calendar ref="calendar">
              <template #header="{ date }">
                <span>{{ date }}</span>
                <el-button-group>
                  <el-button size="small" @click="selectDate('prev-month')">上一月</el-button>
                  <el-button size="small" @click="selectDate('today')">今天</el-button>
                  <el-button size="small" @click="selectDate('next-month')">下一月</el-button>
                </el-button-group>
              </template>
            </el-calendar>
          </div>
        </el-card>
      </el-col>

      <!--绩点-->
      <el-col :span="14">
        <el-card class="second_panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><PieChart /></el-icon>绩点</el-text></span>
            </div>
          </template>
          <div>
            <grade-points-chart style="height: 10px"/>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第三行-->
    <el-row :gutter="20">
      <!--课程表-->
      <el-col :span="24">
        <el-card class="class_panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><School /></el-icon>课程表</el-text></span>
            </div>
          </template>
          <div>
            <class-table/>
          </div>
        </el-card>
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

.system-entrance{
  height: 230px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}

.panel{
  height: 360px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}

.class_panel{
  height: 1400px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}
.second_panel{
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

.chart {
  height: 400px;
}
</style>