// Sidebar imports
import {
  UilEstate,
  UilClipboardAlt,
  UilUsersAlt,
  UilPackage,
  UilChart,
  UilSignOutAlt,
} from "@iconscout/react-unicons";

// Analytics Cards imports
import { UilUsdSquare, UilMoneyWithdrawal } from "@iconscout/react-unicons";
import { keyboard } from "@testing-library/user-event/dist/keyboard";

// Recent Card Imports
import img1 from "../imgs/img1.png";
import img2 from "../imgs/img2.png";
import img3 from "../imgs/img3.png";

// Sidebar Data
export const SidebarDataForTsp = [
  {
    icon: UilEstate,
    heading: "Dashboard",
  },
  {
    icon: UilPackage,
    heading: "About us",
  },
  {
    icon: UilChart,
    heading: "Contact us",
  },
];

export const SidebarDataForCustomer = [
  {
    icon: UilEstate,
    heading: "Dashboard",
  },
  {
    icon: UilClipboardAlt,
    heading: "Track Bookings",
  },

  {
    icon: UilPackage,
    heading: "About us",
  },
  {
    icon: UilChart,
    heading: "Contact us",
  },
];


// Analytics Cards Data
let transporterDetails = [
  {
    name: "Laxmi Transporters",
    ratings: 4.5,
    serviceType: "Car",
    perKmPrice: 10,
  },
  {
    name: "Laxmi Transporters",
    ratings: 4.5,
    serviceType: "House",
    perKmPrice: 10,
  },
  {
    name: "Laxmi Transporters",
    ratings: 4.5,
    serviceType: "Office",
    perKmPrice: 10,
  },

  { name: "Blue Dart", ratings: 4.7, serviceType: "House", perKmPrice: 10 },
  { name: "DTDC Courier", ratings: 4.2, serviceType: "Office", perKmPrice: 10 },
  { name: "Sky Transports", ratings: 3.5, serviceType: "Car", perKmPrice: 10 },

  { name: "Speed Post", ratings: 2.5, serviceType: "Car", perKmPrice: 10 },
  { name: "Blue Dart", ratings: 4.5, serviceType: "House", perKmPrice: 10 },
  {
    name: "Agarwal Packers",
    ratings: 4.5,
    serviceType: "Office",
    perKmPrice: 10,
  },
];

export const getCardsData = (transporterDetails) => {
  let cardsData = [
    {
      title: "",
      transporterId: "",
      officeAddress: "",
      barValue: 70,
      value: "25,970",
      png: UilUsdSquare,
      color: {
        backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
        boxShadow: "0px 10px 20px 0px #e0c6f5",
      },
    },
    {
      title: "Revenue",
      color: {
        backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
        boxShadow: "0px 10px 20px 0px #FDC0C7",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 80,
      value: "14,270",
      png: UilMoneyWithdrawal,
    },
    {
      title: "Expenses",
      color: {
        backGround:
          "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
        boxShadow: "0px 10px 20px 0px #F9D59B",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 60,
      value: "4,270",
      png: UilClipboardAlt,
    },

    {
      title: "Sales",
      color: {
        backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
        boxShadow: "0px 10px 20px 0px #e0c6f5",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 70,
      value: "25,970",
      png: UilUsdSquare,
    },
    {
      title: "Revenue",
      color: {
        backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
        boxShadow: "0px 10px 20px 0px #FDC0C7",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 80,
      value: "14,270",
      png: UilMoneyWithdrawal,
    },
    {
      title: "Expenses",
      color: {
        backGround:
          "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
        boxShadow: "0px 10px 20px 0px #F9D59B",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 60,
      value: "4,270",
      png: UilClipboardAlt,
    },

    {
      title: "Sales",
      color: {
        backGround: "linear-gradient(180deg, #bb67ff 0%, #c484f3 100%)",
        boxShadow: "0px 10px 20px 0px #e0c6f5",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 70,
      value: "25,970",
      png: UilUsdSquare,
    },
    {
      title: "Revenue",
      color: {
        backGround: "linear-gradient(180deg, #FF919D 0%, #FC929D 100%)",
        boxShadow: "0px 10px 20px 0px #FDC0C7",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 80,
      value: "14,270",
      png: UilMoneyWithdrawal,
    },
    {
      title: "Expenses",
      color: {
        backGround:
          "linear-gradient(rgb(248, 212, 154) -146.42%, rgb(255 202 113) -46.42%)",
        boxShadow: "0px 10px 20px 0px #F9D59B",
      },
      transporterId: "",
      officeAddress: "",
      barValue: 60,
      value: "4,270",
      png: UilClipboardAlt,
    },
  ];

  cardsData.map((item, i) => {
    item.title = transporterDetails[i].serviceType;
    item.value = transporterDetails[i].name;
    item.barValue = transporterDetails[i].ratings * 20;
    item.transporterId = transporterDetails[i].transporterId;
    item.officeAddress = transporterDetails[i].officeAddress;
    return item;
  });
  // console.error("CRADS DATA ", cardsData);
  return cardsData;
};

// Recent Update Card Data
export const UpdatesData = [
  {
    img: img1,
    name: "Andrew Thomas",
    noti: "has ordered Apple smart watch 2500mh battery.",
    time: "25 seconds ago",
  },
  {
    img: img2,
    name: "James Bond",
    noti: "has received Samsung gadget for charging battery.",
    time: "30 minutes ago",
  },
  {
    img: img3,
    name: "Iron Man",
    noti: "has ordered Apple smart watch, samsung Gear 2500mh battery.",
    time: "2 hours ago",
  },
];

export function getReviewsDataInProperFormat(reviews){

  if(reviews ){
    UpdatesData.map((item, i) => {
      item.name = reviews[i].customerName;
      item.noti = reviews[i].remark;
      return item;
    });
    return UpdatesData;
  }

}

