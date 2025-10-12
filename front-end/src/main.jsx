import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { ThemeProvider, CssBaseline } from "@mui/material";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <ThemeProvider theme={theme}>
    <CssBaseline /> {/* Global reset for all components */}
      <App />
    </ThemeProvider >
      
  </StrictMode>
);
