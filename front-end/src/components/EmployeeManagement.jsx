import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  MenuItem,
} from "@mui/material";
import { users } from "../data/mockData";
import Select from '@mui/material/Select';

const columns = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Name', width: 130 },
  { field: 'email', headerName: 'Email', width: 130 },
  { field: 'center', headerName: 'Center', width: 130},
  { field: 'roleId', headerName: 'Role', width: 130 },
  { field: 'status', headerName: 'Status', width: 130}
];

const EmployeeManagement = () => {
  return (
    
    <TableContainer
      component={Paper}
      sx={{
        backgroundColor: "#002D72",
        borderRadius: "12px",
        width: "90%",
        margin: "auto",
      }}
    >
      <Typography 
        variant="h2" 
        sx={{
          color: "white",
          textAlign: "center"
        }}>
        Employee Management
      </Typography>
      <Table>
        <TableHead>
          <TableRow>
            { 
              columns.map((column, index) => (
                  <TableCell key={index} sx={{ color: "white", fontWeight: "bold" }}>
                    {column.headerName}
                  </TableCell>
              ))
            }
          </TableRow>
        </TableHead>

        <TableBody>
            {
              users.map((user, index) => (
                <>
                  <TableRow key={index}> 
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.id}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.name}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.email}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.center && user.center.split(' ')[0]}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.roleId && user.roleId == 1 && "Admin"}
                      {user.roleId && user.roleId == 2 && "Employee"}
                      {user.roleId && user.roleId == 3 && "Citizen"}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {user.status && user.status === "Active" && user.status}
                      {(!user.status || user.status === "Inactive") && "Inactive"}
                    </TableCell>
                  </TableRow>
                </>
              ))
            }
        </TableBody>
      </Table>

    </TableContainer>
  )

}

export default EmployeeManagement