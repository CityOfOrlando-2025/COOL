import "./App.css";
import LandingPage from "./pages/LandingPage";
import SignInPage from "./pages/SignInPage";
import DevicePage from "./pages/DeviceAvailabilityPage";
import NearestPage from "./pages/NearestCenterPage";
import { Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar";
import AdminDashboardPage from "./pages/AdminDashboardPage";

function App() {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/signIn" element={<SignInPage />} />
        <Route path="/device" element={<DevicePage />} />
        <Route path="/nearest" element={<NearestPage />} />
        <Route path='/adminDashboard' element={<AdminDashboardPage/>} />
      </Routes>
    </>
  );
}

export default App;
