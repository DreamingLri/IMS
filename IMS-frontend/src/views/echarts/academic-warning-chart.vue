<template>
  <div style="width: 100%; height: 100%">
    <v-chart class="chart" :option="option" style="height: 300px" autoresize/>
  </div>
</template>

<script setup>

let score = JSON.parse(localStorage.getItem("score"))
console.log(score)

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
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    top: 'center'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: [
        { value: score[0], name: '90-100' },
        { value: score[1], name: '80-90' },
        { value: score[2], name: '70-80' },
        { value: score[3], name: '60-70' },
        { value: score[4], name: '0-60' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
});
</script>

<style scoped>
.chart {
}
</style>
