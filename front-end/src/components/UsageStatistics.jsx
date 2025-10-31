import React, { useState, useEffect } from "react";
import {
  Grid,
  Box,
  Typography,
  MenuItem,
} from "@mui/material";
import { PieChart } from '@mui/x-charts/PieChart';
import Select from '@mui/material/Select';
import { BarChart } from "@mui/x-charts";

const months = ['January', 'Feburary', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
const years = ['2025']

const usageData = {
  '2025':{
    'January': {
      'Laptops': 20,
      'hotSpots': 30,
      'Tablets': 50
    },
    'Feburary': {
      'Laptops': 10,
      'hotSpots': 50,
      'Tablets': 40
    },
    'March': {
      'Laptops': 40,
      'hotSpots': 30,
      'Tablets': 30
    },
    'April': {
      'Laptops': 35,
      'hotSpots': 45,
      'Tablets': 25
    },
    'May': {
      'Laptops': 25,
      'hotSpots': 35,
      'Tablets': 50
    },
    'June': {
      'Laptops': 15,
      'hotSpots': 60,
      'Tablets': 30
    },
    'July': {
      'Laptops': 10,
      'hotSpots': 70,
      'Tablets': 20
    },
    'August': {
      'Laptops': 30, 
      'hotSpots': 40,
      'Tablets': 40
    },
    'September': {
      'Laptops': 45,
      'hotSpots': 25,
      'Tablets': 35
    },
    'October': {
      'Laptops': 35,
      'hotSpots': 30,
      'Tablets': 40
    },
    'November': {
      'Laptops': 25,
      'hotSpots': 40,
      'Tablets': 45
    },
    'December': {
      'Laptops': 30,
      'hotSpots': 50,
      'Tablets': 20
    }
  }
}

const UsageStatistics = () => {

  const [month, setMonth] = useState(months[0])
  const [year, setYear] = useState(years[0])
  const [usage, setUsage] = useState([])

  useEffect(() => {
    let currentUsage = usageData[year][month];
    let arr = []
    arr.push({id: 0, value: currentUsage.Laptops, label: 'Laptops'})
    arr.push({id: 1, value: currentUsage.hotSpots, label: 'Hot Spots'})
    arr.push({id: 2, value: currentUsage.Tablets, label: 'Tablets'})
    setUsage(arr)
  }, [month, year]);

  return (
    <>
      <Box
        sx={{
          width: "90%",
          backgroundColor: "#002D72",
          borderRadius: "12px",
          color: "white",
          margin: "auto",
          textAlign: "center"
        }}
      >
        <Typography 
          variant="h2" 
          sx={{
            color: "white",
            textAlign: "center"
          }}
        >
          Usage Statistics
        </Typography>
        <Grid container spacing={2}>
          <Grid size={12}>
            
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={year}
              label="Age"
              sx={{
                color: "white",
                border: "1px solid white",
                '.MuiSvgIcon-root': {
                  color: 'white',
                },
              }}
              onChange={(event)=>{setYear(event.target.value)}}
            >
            {
              years.map((year, index) => (
                <MenuItem key={index} value={year}>{year}</MenuItem>
              ))
            }
            </Select> 
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={month}
              label="Age"
              sx={{
                color: "white",
                border: "1px solid white",
                '.MuiSvgIcon-root': {
                  color: 'white',
                },
              }}
              onChange={(event)=>{setMonth(event.target.value)}}
            >
              {
                months.map((month, index) => (
                  <MenuItem key={index} value={month}>{month}</MenuItem>
                ))
              }
            </Select>

          </Grid>
          <Grid size={6}>
            <PieChart
              series={[
                {
                  data: usage
                },
              ]}
              width={200}
              height={200}
              sx ={{
                '.MuiChartsLegend-label': {
                  color: 'white',
                },
              }}
            />
          </Grid>
          <Grid size={6}>
            {
            /*
            This was working... but for some reason stopped?
            <BarChart
              width={350} 
              height={300}
              dataset={usage}
              xAxis={[{ dataKey: 'label'}]}
              series={[
                {dataKey: 'value', color: 'white'}
              ]}
              sx={{
                '.MuiChartsAxis-bottom .MuiChartsAxis-tickLabel': {
                  fill: 'white',
                },
                '.MuiChartsAxis-left .MuiChartsAxis-tickLabel': {
                  fill: 'white',
                },
                '.MuiChartsAxis-line, .MuiChartsAxis-tick': {
                  stroke: 'white', 
                  strokeWidth: 1,
                }
              }}
            />
            */
            }
          </Grid>
        </Grid>
      </Box>
      
    </>
  )

}

export default UsageStatistics