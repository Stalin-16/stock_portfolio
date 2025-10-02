import { useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import PortfolioService from "../services/PortfolioService.";
import ChartComponent from "../components/ChartComponent";
import AddStockModal from "../components/AddStockModal";
import CreatePortfolioModal from "../components/CreatePortfolioModal";

export default function Dashboard() {
  const [assets, setAssets] = useState([]);
  const [portfolios, setPortfolios] = useState();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const userData = localStorage.getItem("user");
  const [userId, setUserId] = useState(JSON.parse(userData).id);
  const [isCreatePortfolioModalOpen, setIsCreatePortfolioModalOpen] =
    useState(false);
    //Logout
  const { logout } = useAuth();

  const [formData, setFormData] = useState({
    portfolioId: "",
    ticker: "",
    quantity: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchPortfolios();
    fetchAssets();
  }, []);

  const fetchAssets = async () => {
    try {
      setLoading(true);
      const response = await PortfolioService.getPortfolio(1, userId);
      if (response && response.data) {
        setAssets(response.data.data || []);
      } else {
        setError("No data received from API");
      }
    } catch (error) {
      setError("Failed to load portfolio data");
    } finally {
      setLoading(false);
    }
  };

  const fetchPortfolios = async () => {
    try {
      const response = await PortfolioService.getAllPorfolioNames(userId);
      if (response && response.data) {
        setPortfolios(response.data.data || []);
      }
    } catch (error) {
      setError("Failed to load portfolio data");
    }
  };

  const addAsset = async () => {
    try {
      console.log(formData);
      setError("");
      const data = {
        ticker: formData.ticker,
        quantity: formData.quantity,
      };

      const response = await PortfolioService.addAssets(
        formData.portfolioId,
        data
      );
      if (response) {
        fetchAssets();
      }
      setTicker("");
    } catch (error) {
      setError("Failed to add stock");
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
    setFormData({
      portfolioId: portfolios[0]?.id || "",
      ticker: "",
      quantity: "",
    });
    setError("");
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setFormData({ portfolioId: "", ticker: "", quantity: "" });
    setError("");
  };

  const openCreatePortfolioModal = () => {
    setIsCreatePortfolioModalOpen(true);
    setError("");
  };

  const closeCreatePortfolioModal = () => {
    setIsCreatePortfolioModalOpen(false);
    setError("");
  };
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const createPortfolio = async (portfolioData) => {
    try {
      const data = {
        name: portfolioData.name,
      };
      const response = await PortfolioService.addPortfolio(userId, data);
      if (response) {
        console.log(response);
      }
    } catch (error) {
      console.error(e);
    }
  };

  const deleteAsset = async (assetId) => {
    try {
      setError("");
      await PortfolioService.deleteAsset(assetId);
      fetchAssets();
    } catch (error) {
      setError("Failed to delete asset");
    }
  };

  //Loading
  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="flex items-center space-x-3">
          <div className="animate-spin rounded-full h-10 w-10 border-4 border-blue-500 border-t-transparent"></div>
          <span className="text-lg font-semibold text-gray-700">
            Loading portfolio...
          </span>
        </div>
      </div>
    );
  }

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">My Portfolio</h1>
        <button
          onClick={() => {
            logout();
          }}
          className="text-red-600 hover:text-white hover:bg-red-600 border border-red-600 px-3 py-2 rounded-lg transition-colors font-medium flex items-center gap-2"
        >
          <svg
            className="w-4 h-4"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
            />
          </svg>
          Logout
        </button>
      </div>
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <div className="mb-6 flex flex-wrap gap-3">
        <button
          onClick={openCreatePortfolioModal}
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition-colors font-semibold flex items-center gap-2"
        >
          <svg
            className="w-5 h-5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 4v16m8-8H4"
            />
          </svg>
          Create Portfolio
        </button>
        <button
          onClick={openModal}
          className="bg-green-500 hover:bg-green-600 text-white px-6 py-3 rounded-lg transition-colors font-semibold flex items-center gap-2"
        >
          <svg
            className="w-5 h-5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 4v16m8-8H4"
            />
          </svg>
          Add Stock to Portfolio
        </button>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
        <div>
          {assets.length === 0 ? (
            <div className="text-center py-8 text-gray-500">
              <p>No assets in your portfolio yet.</p>
              <p>Add your first stock using the button above!</p>
            </div>
          ) : (
            <div className="grid gap-4">
              {assets.map((asset, index) => (
                <div
                  key={asset.id || index}
                  className="border border-gray-200 p-4 rounded-lg shadow-sm"
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <h3 className="font-bold text-lg">{asset.ticker}</h3>
                      <p className="text-gray-600">{asset.quantity} shares</p>
                    </div>
                    <div className="text-right">
                      <p className="font-semibold">
                        ${asset.currentPrice?.toFixed(2)}
                      </p>
                      <p
                        className={`text-sm ${
                          asset.changePercent >= 0
                            ? "text-green-600"
                            : "text-red-600"
                        }`}
                      >
                        {asset.changePercent?.toFixed(2)}%
                      </p>
                    </div>
                  </div>
                  <div className="mt-2 pt-2 border-t border-gray-100 flex justify-between items-center">
                    <p className="font-semibold">
                      Total Value: ${asset.totalValue?.toFixed(2)}
                    </p>
                    {/* Delete button */}
                    <button
                      onClick={() => deleteAsset(asset.id)}
                      className="text-red-500 hover:text-red-700 text-sm font-semibold"
                    >
                      Remove
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Right side - chart */}
        <div className="flex justify-center items-center">
          {assets.length > 0 && <ChartComponent assets={assets} />}
        </div>
      </div>

      {/* Modals */}
      <AddStockModal
        isOpen={isModalOpen}
        onClose={closeModal}
        formData={formData}
        onInputChange={handleInputChange}
        onAddStock={addAsset}
        portfolios={portfolios}
        error={error}
        loading={loading}
      />
      <CreatePortfolioModal
        isOpen={isCreatePortfolioModalOpen}
        onClose={closeCreatePortfolioModal}
        onCreatePortfolio={createPortfolio}
        loading={loading}
        error={error}
      />
    </div>
  );
}
