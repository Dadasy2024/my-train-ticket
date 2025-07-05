// ===================== API 配置文件 =====================
// 这个文件用来管理所有后端接口的配置信息

// 后端服务器地址（根据你的后端实际运行端口调整）
export const API_BASE_URL = 'http://localhost:8080'

// 创建 axios 实例的配置
export const API_CONFIG = {
  baseURL: API_BASE_URL,           // 基础URL
  timeout: 10000,                  // 请求超时时间（10秒）
  headers: {
    'Content-Type': 'application/json',  // 请求头，告诉后端这是JSON数据
  }
}

// 登录接口地址
export const LOGIN_API = '/api/login'

// 响应状态码
export const HTTP_STATUS = {
  SUCCESS: 200,        // 成功
  UNAUTHORIZED: 401,   // 未授权
  FORBIDDEN: 403,      // 禁止访问
  NOT_FOUND: 404,      // 未找到
  SERVER_ERROR: 500    // 服务器错误
} 