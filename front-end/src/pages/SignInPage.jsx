import React, { useState} from "react";
import { Box, TextField } from "@mui/material";
import bgImage from "/src/assets/The_City_beautiful.jpg";
import Button from "../components/Button";
import { Link } from "react-router-dom";

const SignInPage = () => {

const [formData, setFormData] = useState({
  name: "",
  email: "",
});
  
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

    const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
    // Add form submission logic here
  };

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
      <Box
        sx={{
          display: "flex",
          alignItmes: "center",
          justifyContent: "center",
          flexDirection: "column",
          backgroundColor: "rgba(255, 255, 255, 0.5)",
          width: "20%",
          padding: "50px",
          textAlign: "center",
        }}
      >
        <Box
          component="form"
          onSubmit={handleSubmit}
          sx={{ display: "flex", flexDirection: "column", gap: 5 }}
        >
          <TextField
            label="Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            fullWidth
            sx={{
              "& .MuiOutlinedInput-root": {
                borderRadius: "5px",
              },
            }}
          />
          <TextField
            label="Password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            fullWidth
            sx={{
              "& .MuiOutlinedInput-root": {
                borderRadius: "5px",
              },
            }}
          />
          <Button type="submit" varianttype="sign">
            Sign In
          </Button>
          <Link
            to="/signIn"
            style={{
              color: "#2C2C2C",
              fontStyle: "italic",
              textDecoration: "underline",
              textAlign: "left",
            }}
          >
            Forgot password?
          </Link>
        </Box>
      </Box>
    </Box>
  );
};

export default SignInPage;
