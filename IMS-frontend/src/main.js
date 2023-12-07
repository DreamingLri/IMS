import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import router from './router'
import 'element-plus/dist/index.css'
import App from './App.vue'
import {createPinia} from "pinia";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import ECharts from "vue-echarts";
import "echarts"

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.component("echarts", ECharts)
app.use(ElementPlus, {
    locale: zhCn,
})
app.use(router)
app.use(createPinia())
app.mount('#app')
