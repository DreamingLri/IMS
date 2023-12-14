<script setup>

import {ArrowRight, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import VChart from "vue-echarts";


const course = ref('')
const courseList = ref([])
const scoreDialog = ref(false)
const name = ref('')
let user = JSON.parse(localStorage.getItem("user"))

//Echarts
const selectNum = ref()
const totalNum = ref()
const leftNum = ref()

function getCourseList(){
  request.get('/course/listNumberOfStudentSelectCourseByUserId?userId='+user.id).then(res => {
    if(res.code === 200){
      courseList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}
onMounted(() =>{
  getCourseList()
})

function openScoreDialog(row){
  scoreDialog.value = true
  totalNum.value = row.studentNumber
  selectNum.value = row.selectedNumber
  leftNum.value = totalNum.value - selectNum.value
}

function closeScoreDialog(){
  scoreDialog.value = false;
}

const rules = reactive({
})

//Echarts
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

const option = ref({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center',
    selectedMode: false
  },
  series: [
    {
      name: '选课情况表',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['50%', '70%'],
      startAngle: 180,
      label: {
        show: true,
        formatter(param) {
          return param.name + ' (' + param.percent * 2 + '%)';
        }
      },
      data: [
        { value: selectNum, name: '选课人数' },
        { value: leftNum, name: '剩余人数' },
        {
          value: totalNum,
          itemStyle: {
            color: 'none',
            decal: {
              symbol: 'none'
            }
          },
          label: {
            show: false
          }
        }
      ]
    }
  ]
});
</script>

<template>
  <div class="main-wrapper">
    <div style="height: 5%">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/teacher/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>选课情况</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <div style="width: 100%; height: 40%; display: flex">
          <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
          <el-button @click="getCourseList()" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
        </div>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="courseList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="courseName" label="课程名称" width="130" />
        <el-table-column prop="teacher" label="教师" width="130" />
        <el-table-column prop="place" label="上课地点" width="130" />
        <el-table-column prop="studentNumber" label="开课人数" width="130" />
        <el-table-column prop="selectedNumber" label="选课人数" width="130" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openScoreDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>

  <el-dialog
      v-model="scoreDialog"
      title="选课详情"
      width="30%"
      :before-close="closeScoreDialog"
      style="border-radius: 8px"
  >
    <div style="width: 100%; height: 100%">
      <v-chart class="chart" :option="option" style="height: 400px" autoresize/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeScoreDialog">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.main-wrapper{
  width: 100%;
  height: 100%;
  background-color: white;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 20px;
}

.header-wrapper{
  width: 100%;
  height: 10%;
}
.table-wrapper{
  height: 85%;
  width: 100%;
}
</style>