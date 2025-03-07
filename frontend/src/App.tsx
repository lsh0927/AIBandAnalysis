import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './AuthContext';
import { Toaster } from 'react-hot-toast'; // 추가
import Login from './Login';
import Dashboard from './components/Dashboard';
import OAuth2RedirectHandler from './OAuth2RedirectHandler';
import ProjectForm from './components/ProjectForm';
import ProjectDetail from './components/ProjectDetail';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-50">
          {/* Toaster 추가 */}
          <Toaster position="top-right" reverseOrder={false} />

          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
            <Route path="/project" element={<ProjectForm />} />
            <Route path="/project/:id" element={<ProjectDetail />} />
            <Route path="/" element={<Navigate to="/login" />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;
