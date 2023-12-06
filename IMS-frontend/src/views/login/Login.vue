<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import Title from "@/components/Title.vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useInfoStore} from "@/stores/pinna"

const useStore = useInfoStore();

const login = () =>{
  request.post("/login", loginForm).then(res =>{
    if(res.code === 200){
      useStore.user = res.data;
      console.log(useStore.user)
      ElMessage.success("登录成功")
      router.push('/index')
    } else {
      ElMessage.error(res.message)
    }
  })
}
const loginForm = reactive({
  username: '',
  password: ''
})


</script>

<template>
  <div class="main-wrapper">
    <Title/>
    <div class="wrapper">
      <div class="common-layout">
        <el-container>
          <el-header class="header" height="130px">
            <div>
              <img class="casimg" src="../../../public/caslogo.png" alt="caslogo">
            </div>
          </el-header>
          <el-main class="main">
            <el-input class="inputID" v-model="loginForm.username" placeholder="NetId" />
            <el-input class="inputPW" v-model="loginForm.password" placeholder="密 码" />
            <el-button class="loginBtn" type="success" @click="login">登 录</el-button>
            <el-divider/>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-button class="Btn" type="primary" @click="showSignCard">注 册</el-button>
              </el-col>
              <el-col :span="12">
                <el-button class="Btn" type="primary" @click="showHelpCard">帮 助</el-button>
              </el-col>
            </el-row>
          </el-main>
        </el-container>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header{
  background-color: rgb(48,96,60);
  border-radius: 10px;
}
.casimg{
  margin-top: 20px;
}
.inputPW{
  margin-top: 10px;
}
.loginBtn{
  width: 100%;
  margin-top: 20px;
}
.common-layout{
  background-color: aliceblue;
  margin-top: 30px;
  border-radius: 10px;
}
.wrapper{
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.Btn{
  width: 100%;
}
.main-wrapper{
  margin-top: 60px;
}

.el-card{
  background-color: aliceblue;
}
</style>