import React from "react";
import Login from "./Components/Login/Login";// src/App.jsx
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AdminDashboard from './Components/Dashboard/admin-dashboard/admindashboard';
import UserDashboard from './Components/Dashboard/user-dashboard/userdashboard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/user-dashboard" element={<UserDashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
