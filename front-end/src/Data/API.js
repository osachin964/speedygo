import { getCardsData } from "./Data";

export const BOOKING_API_ENDPOINT =
  " http://localhost:8080/bookingService";
export const PAYMENT_API_ENDPOINT =
  " http://localhost:8080/paymentService";
export const TRANSPORTER_API_ENDPOINT =
  " http://localhost:8080/transporterService"; //change POST to GET
export const REGISTRATION_API_ENDPOINT =
  " http://localhost:8080/registrationService"; //change POST to GET
export const REVIEW_API_ENDPOINT =
  " http://localhost:8080/reviewService";

export async function fetchPriceApi() {
  let customer = JSON.parse(localStorage.getItem("customer"));
  var payload = {
    to: customer.dropLocation,
    from: customer.pickupLocation,
  };

  const resp = await fetch(
    BOOKING_API_ENDPOINT + "/get-price/" + customer.transporterId,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    }
  ).then((t) => t.json());
  customer.totalPrice = resp.price;
  localStorage.setItem("customer", JSON.stringify(customer));
  return resp;
}

export async function generateQuoteApi() {
  let quote = JSON.parse(localStorage.getItem("quotation"));
  var payload = {
    to: quote.dropLocation,
    from: quote.pickupLocation,
  };

  const resp = await fetch(BOOKING_API_ENDPOINT + "/generate-quotation/", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  }).then((t) => t.json());
  return resp.TransporterList;
}

export async function updateBookingStatus(a,b) {
  const resp = await fetch(
    BOOKING_API_ENDPOINT +
      "/update-booking-status/" +
      a +
      "/" +
      b,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: "JMD",
    }
  ).then((t) => t.json());
  return resp;
}

export async function fetchBookingsForTransporter(transporterId) {
  const loggedInUser = JSON.parse(localStorage.getItem("user"));

  const resp = await fetch(
    BOOKING_API_ENDPOINT + "/get-booking-by-transporterId/" + loggedInUser.id,
    {
      method: "GET",
      // headers: {
      //   "Content-Type": "application/json",
      // },
      // body: "JMD",
    }
  ).then((t) => t.json());
  return resp;
}

export async function getAllTransporters() {
  const resp = await fetch(TRANSPORTER_API_ENDPOINT + "/getAllTransporters", {
    method: "GET",
    // headers: {
    //   "Content-Type": "application/json",
    // },
    // body: "JMD",
  });

  let data = await resp.json();
  const parsedData = data.map((obj) => {
    let temp = {
      name: obj.companyName,
      ratings: obj.rating ? obj.rating : 5,
      serviceType: obj.experience,
      perKmPrice: obj.pricePerKm,
      transporterId: obj.transporterEmail,
      officeAddress: obj.officeAddress,
    };
    return temp;
  });
  // console.log("get all tsp data ", parsedData);

  return getCardsData(parsedData);
}

export async function fetchUserDetails(emailId) {
  const resp = await fetch(REGISTRATION_API_ENDPOINT + "/getUser/" + emailId, {
    method: "GET",
    // headers: {
    //   "Content-Type": "application/json",
    // },
    // body: "JMD",
  }).then((t) => t.json());
  return resp;
}
export async function fetchBookingDetails(bId) {
    const resp = await fetch(BOOKING_API_ENDPOINT + "/view-status/" + bId, {
      method: "GET",
      // headers: {
      //   "Content-Type": "application/json",
      // },
      // body: "JMD",
    }).then((t) => t.json());
    return resp;
  }
  


export async function fetchReviewsForTransporter(transporterId) {
  console.log("Inside fetch reviews", transporterId);

  let tspId = transporterId != null ? transporterId : "bluedart@gmail.com";

  const resp = await fetch(REVIEW_API_ENDPOINT + "/getReview/" + tspId, {
    method: "GET",
    // headers: {
    //   "Content-Type": "application/json",
    // },
    // body: "JMD",
  }).then((t) => t.json());
  return resp;
}