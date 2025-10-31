import React from 'react'
import DeviceInventory from "../components/DeviceInventory";
import { locations } from '../data/mockData';
import UsageStatistics from '../components/UsageStatistics';
import IncidentLogs from '../components/IncidentLogs';
import EmployeeManagement from '../components/EmployeeManagement';




const AdminDashboardPage = () => {


  return (
    <>

      <DeviceInventory/>
      <UsageStatistics/>
      <IncidentLogs/>
      <EmployeeManagement/>
    </>


  )
}

export default AdminDashboardPage