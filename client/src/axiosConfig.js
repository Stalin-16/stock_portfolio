import axios from "axios";

const instance = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
});

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // Handle error globally
    console.log("Axios error:", error);
    return Promise.reject(error);
  }
);

// Set up response interceptor
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const { status } = error.response;
      if (status === 401 || status === 403) {
        localStorage.removeItem("encodedCredentials");
      }
      return Promise.reject(error);
    } else {
      return Promise.reject(error);
    }
  }
);
export default instance;
