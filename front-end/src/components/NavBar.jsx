import { AppBar, Toolbar, IconButton, Box, Link } from "@mui/material";
import SettingsOutlined from "@mui/icons-material/SettingsOutlined";
import AccountCircleOutlined from "@mui/icons-material/AccountCircleOutlined";

export const NavBar = () => {
  return (
    <>
      {/* Main NavBar */}
      <AppBar position="static">
        <Toolbar>
          <Box
            component="img"
            sx={{
              padding: "40px",
              width: 500,
              margin: "auto",
            }}
            alt="Your logo."
            src={"./src/assets/fullCityLogo.png"}
          />
        </Toolbar>
      </AppBar>

      <br />

      {/* NavBar with "Help" and "Login" links */}
      <AppBar position="static">
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
            alt="Your logo."
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
              href="#"
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
              href="#"
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

      <br />

      {/* Nav bar with "Settings" and "User" icons */}
      <AppBar position="static">
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
            alt="Your logo."
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
                  fontSize: "xxx-large",
                }}
              ></SettingsOutlined>
            </IconButton>
            <IconButton>
              <AccountCircleOutlined
                sx={{
                  color: "white",
                  fontSize: "xxx-large",
                }}
              ></AccountCircleOutlined>
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>
    </>
  );
};

export default NavBar;
