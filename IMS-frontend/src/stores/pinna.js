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
            id: 1,
            identificationCode: '1111111111111111111',
            netId: 'admin',
            password: '123456',
            username: '测试员',
            total_credit: '166',
            earned_credit: '56',
            grade_points: '3.4',
            affiliated_school: '软件工程学院',
            evaluation_score: '90.3',
            qualification: '副教授助理',
            research_direction: '人工智能、机器学习'
        }
    }
})