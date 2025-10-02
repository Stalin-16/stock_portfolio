import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const AuthRoute = ({ children }) => {
  const { isAuthenticated, loading } = useAuth();

  if (loading) {
    return <div>Loading...</div>;
  }

  if (isAuthenticated) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
};

export default AuthRoute;