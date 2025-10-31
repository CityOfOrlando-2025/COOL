import * as React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography, 
} from "@mui/material";

const columns = [
  { field: 'centerName', headerName: 'Center Name', width: 70 },
  { field: 'laptops', headerName: 'Laptops (Avail/Total)', width: 130 },
  { field: 'tablets', headerName: 'Tablets (Avail/Total)', width: 130 },
  { field: 'hotspots', headerName: 'Hotspots (Avail/Total)', width: 130 },
  { field: 'totalDevices', headerName: 'Total Devices (%)', width: 130 },
];

const rows = [
  { centerName: 'Callahan', laptops: '5 / 10', tablets: '3 / 8', hotspots: '7 / 10', totalDevices: '15 / 28 (54%)'},
  { centerName: 'Smith', laptops: '2 / 8', tablets: '6 / 8', hotspots: '5 / 8', totalDevices: '13 / 24 (54%)'},
  { centerName: 'Jackson', laptops: '4 / 6', tablets: '2 / 6', hotspots: '6 / 10', totalDevices: '12 / 22 (55%)'},
  { centerName: 'Wadeview', laptops: '6 / 12', tablets: '5 / 10', hotspots: '8 / 12', totalDevices: '19 / 34 (56%)'},
  { centerName: 'Engelwood', laptops: '3 / 7', tablets: '4 / 8', hotspots: '2 / 6', totalDevices: '9 / 21 (43%)'},
  { centerName: '', laptops: '20 / 43 (47%)', tablets: '20 / 40 (50%)', hotspots: '28 / 46 (61%)', totalDevices: '68 / 129 (53%)'},
]


const DeviceInventory = () => {
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
        Device Inventory
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
              rows.map((row, index) => (
                <>
                  <TableRow key={index}> 
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {row.centerName}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {row.laptops}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {row.tablets}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {row.hotspots}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {row.totalDevices}
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

export default DeviceInventory