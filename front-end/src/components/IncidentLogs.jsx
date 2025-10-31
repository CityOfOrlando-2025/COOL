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
import { incidentLogs, locations } from "../data/mockData";
import Select from '@mui/material/Select';

const columns = [
  { field: 'date', headerName: 'Date', width: 70 },
  { field: 'resident', headerName: 'Resident Name', width: 130 },
  { field: 'device', headerName: 'Device', width: 130 },
  { field: 'center', headerName: 'Center', width: 130 },
  { field: 'issue', headerName: 'Issue', width: 130 },
  { field: 'status', headerName: 'Status', width: 130 },
  { field: 'action', headerName: 'Action', width: 130 },
];


const IncidentLogs = () => {

  const [selectedCenter, setSelectedCenter] = useState('');
  const [centers, setCenters] = useState([])
  const [filteredLogs, setFilteredLogs] = useState(incidentLogs)
  useEffect(() => {
    let temp = []
    locations.forEach((location) => temp.push(location.split(' ')[0]))
    setCenters(temp)
    setSelectedCenter(temp[0]) 
  }, [])

  useEffect(() => {
    if (!selectedCenter) {
      setFilteredLogs(incidentLogs);
      return;
    }
    
    const newLogs = incidentLogs.filter((entry) => {return entry.center.startsWith(selectedCenter)});
    setFilteredLogs(newLogs)

  }, [selectedCenter])

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
        Incident Logs
      </Typography>
      <Select
        labelId="demo-simple-select-label"
        id="demo-simple-select"
        value={selectedCenter}
        color="white"
        sx={{
          color: "white",
          border: "1px solid white",
          '.MuiSvgIcon-root': {
            color: 'white',
          },
        }}
        onChange={(event)=>{setSelectedCenter(event.target.value)}}
      >
        {
          centers.map((center, index) => (
            <MenuItem key={index} value={center}>{center}</MenuItem>
          ))
        }
      </Select>
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
              filteredLogs.map((log, index) => (
                <>
                  <TableRow key={index}> 
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.date}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.resident}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.device}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.center}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.issue}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.status}
                    </TableCell>
                    <TableCell sx={{ color: "white", fontWeight: "bold" }}>
                      {log.action}
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

export default IncidentLogs