import axiosInstance from "../axiosConfig";

const CreateData = async (path, data) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  try {
    const response = await axiosInstance.post(path, data, config);
    return response.data;
  } catch (error) {
    console.error(`Error creating ${path}:`, error);
    return error.response.data;
  }
};

const GetAllData = async (path) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  try {
    const res = await axiosInstance.get(path, config);

    return res;
  } catch (error) {
    console.error(`Error fetching ${path}s:`, error);
    return error.response.data;
  }
};

const DeleteData = async (path, id) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  try {
    const response = await axiosInstance.delete(`${path}/${id}`, config);
    return response.data;
  } catch (error) {
    console.error(`Error deleting ${path}:`, error);
    return error.response.data;
  }
};
const UpdateData = async (path, data) => {
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axiosInstance.put(path, data, config);
    return response.data;
  } catch (error) {
    console.error(`Error creating ${path}:`, error);
    return error.response.data;
  }
};

const RestService ={CreateData, GetAllData, DeleteData, UpdateData}

export default RestService;
