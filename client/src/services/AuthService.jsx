import axios from "axios";

const AuthService = {
  login: (username, password) => {
    const credentials = { username, password };
    return axios.post(
      import.meta.env.VITE_BASE_URL + "/auth/login",
      credentials
    );
  },
  Signup:(data)=>{
    return axios.post(
      import.meta.env.VITE_BASE_URL + "/auth/signup",
      data
    );
  }
};

export default AuthService;