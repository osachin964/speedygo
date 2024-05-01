import React,{useState,useEffect} from 'react';
import Typography from '@mui/material/Typography';
import { fetchPriceApi } from '../../Data/API';
import LinearProgress from '@mui/material/LinearProgress';


export default function PaymentForm() {
    const [data, setData] = useState(null);

    useEffect(() => {
        async function fetchData() {
            const response = await fetchPriceApi();
            const data = response;
            // console.log("fetched data ", data)
            setData(data);
          }
          fetchData();
    }, []);

       
    
  return (
    <React.Fragment>
                

      <Typography variant="h6" gutterBottom>

      {data?"Estimated price for "+data.distance+" kms is: "+data.price.toFixed(2)+" rs.":
                   (<Typography variant="h6" gutterBottom>
                    Loading Price Details....
                <LinearProgress />
                </Typography>)}
      </Typography>
    
    </React.Fragment>
  );
}
