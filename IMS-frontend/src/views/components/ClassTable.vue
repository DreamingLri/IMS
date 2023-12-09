<script setup>
import {reactive} from "vue";
import {Location, User} from "@element-plus/icons-vue";

const tableData = reactive([
  {
    session: {num: '第一节', time: '8:00-8:45'}, mon: {course: '离散数学', room: '教学楼 C301', teacher: '何笑雨'}
  }
])
</script>

<template>
<el-table border stripe :data="tableData" :header-cell-style="{background:'#eee',color:'#606266'}">
  <el-table-column label="节次">
    <template v-slot="scope">
      <div v-if="scope.row.session">
        <div style="margin: 10px 0; text-align: center;">{{scope.row.session.num}}</div>
        <div style="margin: 10px 0; text-align: center; color: rgb(145,208,164)">{{scope.row.session.time}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期日">
    <template v-slot="scope">
      <div v-if="scope.row.sun">
        <div style="margin: 10px 0">{{scope.row.sun.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.sun.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.sun.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期一">
    <template v-slot="scope">
      <div v-if="scope.row.mon">
        <div style="margin: 10px 0">{{scope.row.mon.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.mon.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.mon.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期二">
    <template v-slot="scope">
      <div v-if="scope.row.tue">
        <div style="margin: 10px 0">{{scope.row.tue.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.tue.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.tue.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期三">
    <template v-slot="scope">
      <div v-if="scope.row.wed">
        <div style="margin: 10px 0">{{scope.row.wed.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.wed.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.wed.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期四">
    <template v-slot="scope">
      <div v-if="scope.row.thu">
        <div style="margin: 10px 0">{{scope.row.thu.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.thu.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.thu.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期五">
    <template v-slot="scope">
      <div v-if="scope.row.fri">
        <div style="margin: 10px 0">{{scope.row.fri.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.fri.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.fri.room}}</div>
      </div>
    </template>
  </el-table-column>
  <el-table-column label="星期六">
    <template v-slot="scope">
      <div v-if="scope.row.sat">
        <div style="margin: 10px 0">{{scope.row.sat.course}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.sat.teacher}}</div>
        <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.sat.room}}</div>
      </div>
    </template>
  </el-table-column>
</el-table>
</template>

<style scoped>
.headerBg{
  background: #eee!important;
}
</style>