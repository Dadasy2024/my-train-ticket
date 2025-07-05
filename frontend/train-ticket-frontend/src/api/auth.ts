// ===================== 认证相关 API 服务 =====================
// 这个文件专门处理登录、注册等认证相关的接口调用

import axios from 'axios'
import { API_CONFIG, LOGIN_API } from './config'

// 创建 axios 实例
const apiClient = axios.create(API_CONFIG)

// 定义登录请求的数据类型
export interface LoginRequest {
  username: string
  password: string
}

// 定义登录响应的数据类型
export interface LoginResponse {
  code: number
  message: string
  data?: {
    userId: number
    username: string
    // 可以根据后端实际返回的数据结构调整
  }
}

// 登录函数
export async function login(loginData: LoginRequest): Promise<LoginResponse> {
  try {
    // 发送 POST 请求到后端登录接口
    const response = await apiClient.post<LoginResponse>(LOGIN_API, loginData)
    
    // 返回后端响应的数据
    return response.data
  } catch (error) {
    // 处理网络错误或其他异常
    console.error('登录请求失败:', error)
    
    // 如果是 axios 错误，返回错误信息
    if (axios.isAxiosError(error)) {
      if (error.response) {
        // 服务器返回了错误状态码
        return {
          code: error.response.status,
          message: error.response.data?.message || '登录失败，请检查网络连接'
        }
      } else if (error.request) {
        // 请求已发出但没有收到响应
        return {
          code: 0,
          message: '无法连接到服务器，请检查后端服务是否启动'
        }
      }
    }
    
    // 其他未知错误
    return {
      code: -1,
      message: '登录过程中发生未知错误'
    }
  }
}

// 检查用户是否已登录（可以从 localStorage 或 sessionStorage 中获取）
export function isLoggedIn(): boolean {
  const token = localStorage.getItem('userToken')
  return !!token
}

// 保存登录信息到本地存储
export function saveLoginInfo(userData: any) {
  localStorage.setItem('userToken', userData.token || '')
  localStorage.setItem('userInfo', JSON.stringify(userData))
}

// 清除登录信息
export function clearLoginInfo() {
  localStorage.removeItem('userToken')
  localStorage.removeItem('userInfo')
} 