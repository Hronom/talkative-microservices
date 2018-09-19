rs.initiate(
   {
      _id: "configserver",
      configsvr: true,
      members: [
         { _id: 0, host : "config01:27020" },
         { _id: 1, host : "config02:27020" },
         { _id: 2, host : "config03:27020" }
      ]
   }
)