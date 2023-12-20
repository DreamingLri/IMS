<template>
  <div style="width: 100%; height: 100%">
    <v-chart class="chart" :option="option" style="height: 400px" autoresize/>
  </div>
</template>

<script setup>
import request from "@/utils/request";

const userInfo = useInfoStore()
let user = JSON.parse(localStorage.getItem("user"))

// function getGradePoints(){
//   request.get("score/getGradePointByUserId?userId="+user.id).then(res=>{
//     if(res.code === 200){
//       option.value.series.data.value = res.data.toFixed(2)
//       console.log(gradePoints.value)
//     } else {
//       console.log(res.message)
//     }
//   })
// }

import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { PieChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, { THEME_KEY } from 'vue-echarts';
import {ref, provide, onMounted} from 'vue';
import {useInfoStore} from "@/stores/pinna";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

const option = ref({
  series: [
    {
      type: 'gauge',
      startAngle: 90,
      endAngle: -270,
      min: 0,
      max: 5,
      pointer: {
        show: false
      },
      progress: {
        show: true,
        overlap: false,
        roundCap: true,
        clip: false,
        itemStyle: {
          borderWidth: 1,
          borderColor: '#464646'
        }
      },
      axisLine: {
        lineStyle: {
          width: 40
        }
      },
      splitLine: {
        show: false,
        distance: 0,
        length: 10
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        show: false,
        distance: 50
      },
      data: [{value: user.gradePoints , name: '当前平均绩点'}],
      title: {
        fontSize: 20
      },
      detail: {
        width: 50,
        height: 14,
        fontSize: 14,
        color: 'inherit',
        borderColor: 'inherit',
        borderRadius: 20,
        borderWidth: 1,
        formatter: '{value}'
      }
    }
  ]
});
</script>

<style scoped>
.chart {
}
</style>
