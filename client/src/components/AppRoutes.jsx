import { Navigate, Route, Routes } from "react-router-dom";
import AuthRoute from "./AuthRoute";
import Login from "../screens/Login";
import SignUp from "../screens/SignUp";
import ProtectedRoute from "./ProtectedRoute";
import Dashboard from "../screens/Dashboard";
import { useAuth } from "../contexts/AuthContext";

function AppRoutes() {
  const { isAuthenticated, loading } = useAuth();

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <Routes >
      <Route

        path="/login"
        element={
          <AuthRoute>
            <Login/>
          </AuthRoute>
        }
      />
      <Route
        path="/signup"
        element={
          <AuthRoute>
            <SignUp />
          </AuthRoute>
        }
      />
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/"
        element={
          isAuthenticated ? <Navigate to="/dashboard" replace /> : <Login />
        }
      />

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
 
export default AppRoutes;