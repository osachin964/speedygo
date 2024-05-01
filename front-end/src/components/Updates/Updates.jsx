import React,{useState,useEffect} from 'react';
import "./Updates.css";
import { getReviewsDataInProperFormat, UpdatesData } from "../../Data/Data";
import { fetchReviewsForTransporter, fetchUserDetails } from '../../Data/API';
import { Typography } from '@mui/material';

const Updates = (props) => {
  const [data, setData] = useState(null);
  // const [tId, settId] = useState(props.selectedTsp);
  useEffect(() => {

        async function fetchData() {
          if(props.selectedTsp==null){
            setData(null)
          }
          else{
          const response = await fetchReviewsForTransporter(props.selectedTsp);
          const data = getReviewsDataInProperFormat(response);
          setData(data);
          console.error("FETCHED REVIEWS FOR ",props.selectedTsp,response)
          }
        }
        fetchData();
        console.log("INSIDE REVIEWS WITH PROPS",props)

  }, [props.selectedTsp]);


  return (
    <div className="Updates">
      {data? data.map((update) => {
        return (
          <div className="update">
            <img src={update.img} alt="profile" />
            <div className="noti">
              <div  style={{marginBottom: '0.5rem'}}>
                <span>{update.name}</span>
                <span> {update.noti}</span>
              </div>
                <span>{update.time}</span>
            </div>
          </div>
        );
      }): <Typography>Select Any Transporter To View Their Reviews...</Typography>}
    </div>
  );
};

export default Updates;
