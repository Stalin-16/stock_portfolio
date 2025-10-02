import RestService from "./RestServices";
const basePath = "/portfolio"

const PortfolioService = {
  getPortfolio: (portfolio, userid) => {
    return RestService.GetAllData(`${basePath}/${portfolio}/${userid}`);
  },
  getAllPorfolioNames: (userid) => {
    return RestService.GetAllData(`${basePath}/get-all-portfolio/${userid}`);
  },
  addPortfolio:(userid,data)=>{
    return RestService.CreateData(`${basePath}?userId=${userid}`,data);
  },
  addAssets: (portfolioId, data) => {
    return RestService.CreateData(`${basePath}/${portfolioId}/assets`, data);
  },
  deleteAsset:(assetId)=>{
    return RestService.DeleteData(`${basePath}/assets`,assetId)
  }
};

export default PortfolioService;
