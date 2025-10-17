import React from "react";
import { Box, Typography } from "@mui/material";
import bgImage from "/src/assets/The_City_beautiful.jpg";

const LandingPage = () => {
  return (
    <Box
      sx={{
        height: "100vh",
        width: "100vw",
        backgroundImage: `url(${bgImage})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        color: "white",
      }}
    >
      <Typography variant="h2">Welcome to the Landing Page</Typography>
    </Box>
  );
};

export default LandingPage;
