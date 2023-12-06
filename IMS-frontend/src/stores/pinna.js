import { defineStore } from "pinia";
import {ref} from "vue";
export const useInfoStore = defineStore('userInfo', ()=>{
    return{
        user: {
            address: '双鸭山大学',
            birthday: '2022-03-22',
            code: '114514',
            entryTime: '2023-11-00',
            finishTime: '2023-11-01',
            gender: '男',
            id: '1',
            identificationCode: '1111111111111111111',
            netId: 'admin',
            password: '123456',
            username: 'admin',
        }
    }
})