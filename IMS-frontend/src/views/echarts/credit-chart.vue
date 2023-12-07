<template>
  <div style="width: 100%; height: 100%">
    <v-chart class="chart" :option="option" style="height: 400px" autoresize/>
  </div>
</template>

<script setup>
const userInfo = useInfoStore()
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
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: '学分情况',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 20,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: userInfo.user.earned_credit, name: '已获得学分' },
        { value: userInfo.user.total_credit - userInfo.user.earned_credit, name: '剩余学分' },
      ]
    }
  ]
});
</script>

<style scoped>
.chart {
}
</style>
