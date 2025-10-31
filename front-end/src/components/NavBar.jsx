import { AppBar, Toolbar, IconButton, Box, Link } from "@mui/material";
import SettingsOutlined from "@mui/icons-material/SettingsOutlined";
import AccountCircleOutlined from "@mui/icons-material/AccountCircleOutlined";
import { useAuth } from "../context/MockAuth";
import { useLocation } from "react-router-dom";

const NavBar = () => {
  const { user } = useAuth();
  const role = user?.role || "Citizen";
  const location = useLocation();

  // ✅ Show top bar on landing *and* sign-in pages
  const showTopBar =
    location.pathname === "/" || location.pathname === "/signIn";

  // ✅ Function to return navbar layout based on role
  const renderNavbar = () => {
    // 🌆 For Employees or Admins
    if (role !== "Citizen") {
      return (
        <AppBar
          position="static"
          elevation={0}
          sx={{ backgroundColor: "#0072CE" }}
        >
          <Toolbar
            sx={{
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <Box
              component="img"
              sx={{
                padding: "30px",
                width: 150,
              }}
              alt="Fountain Logo"
              src={"./src/assets/fountainLogo2.png"}
            />
            <Box
              sx={{
                display: "flex",
                justifyContent: "space-evenly",
                paddingRight: "30px",
                width: 150,
              }}
            >
              <IconButton>
                <SettingsOutlined
                  sx={{
                    color: "white",
                    fontSize: "5rem",
                  }}
                />
              </IconButton>
              <IconButton>
                <AccountCircleOutlined
                  sx={{
                    color: "white",
                    fontSize: "5rem",
                  }}
                />
              </IconButton>
            </Box>
          </Toolbar>
        </AppBar>
      );
    }

    // 🧍 For Citizens
    return (
      <AppBar
        position="static"
        elevation={0}
        sx={{ backgroundColor: "#0072CE" }}
      >
        <Toolbar
          sx={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <Box
            component="img"
            sx={{
              padding: "30px",
              width: 150,
            }}
            alt="Fountain Logo"
            src={"./src/assets/fountainLogo2.png"}
          />
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              width: 225,
              padding: "55px 60px 55px 55px",
            }}
          >
            <Link
              component="button"
              color="inherit"
              underline="hover"
              sx={{
                fontSize: 30,
                fontWeight: "bold",
              }}
            >
              HELP
            </Link>
            <Link
              component="button"
              color="inherit"
              underline="hover"
              sx={{
                fontSize: 30,
                fontWeight: "bold",
              }}
            >
              LOGIN
            </Link>
          </Box>
        </Toolbar>
      </AppBar>
    );
  };

  return (
    <>
      {/* 🏙️ Show full logo on Landing + SignIn pages */}
      {showTopBar && (
        <AppBar
          position="static"
          elevation={0}
          sx={{ backgroundColor: "#0072CE" }}
        >
          <Toolbar>
            <Box
              component="img"
              sx={{
                padding: "40px",
                width: 500,
                margin: "auto",
              }}
              alt="City of Orlando Logo"
              src={"./src/assets/fullCityLogo.png"}
            />
          </Toolbar>
        </AppBar>
      )}

      {/* 🔄 Show role-based navbar for all other pages */}
      {!showTopBar && renderNavbar()}
    </>
  );
};

export default NavBar;
