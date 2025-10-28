import * as React from "react";
import { FormControl, InputLabel, Select, MenuItem } from "@mui/material";

const FilterCenter = () => {
  //state for selected center
  const [center, setCenter] = React.useState("");

  //handler for when a center is selected
  const handleChange = (event) => {
    setCenter(event.target.value);
  };

  //to be replaced with actual center data later
  let centers = [
    { name: "Example 1" },
    { name: "Example 2" },
    { name: "Example 3" },
    { name: "Example 4" },
  ];

  return (
    <>
      <FormControl
        sx={{ margin: 2, minWidth: 400, backgroundColor: "white", borderRadius: 1 }}
        size="small"
      >
        <InputLabel id="center-filter-label">
          <i>Select a center</i>
        </InputLabel>
        <Select
          labelId="center-filter-label"
          id="center-filter"
          value={center}
          label="Select a center"
          onChange={handleChange}
        >
          <MenuItem value="">None</MenuItem>
          {/* loop through centers to create menu items */}
          {centers.map((center) => (
            <MenuItem value={center.name}>{center.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
    </>
  );
};

export default FilterCenter;
