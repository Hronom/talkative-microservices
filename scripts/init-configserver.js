rs.initiate(
   {
      _id: "configserver",
      configsvr: true,
      members: [
         { _id: 0, host : "config01:28017" },
         { _id: 1, host : "config02:28018" },
         { _id: 2, host : "config03:28019" }
      ]
   }
)