// var quiz = {
//     // (A) PROPERTIES
//     // (A1) QUESTIONS & ANSWERS
//     // Q = QUESTION, O = OPTIONS, S = Sedan, H = Hatchback, ST: Station Wagon, SUV: SUV
//     data: [
//         {
//             q : "Which of these is most important to you when it comes to your vehicle?",
//             o : [
//                 "How sporty it is.",
//                 "Affordable price.",
//                 "How much cargo space it has.",
//                 "How luxurious it is."
//             ],
//             s : 0,// Sedan
//             h : 1,// Hatchback
//             st : 2,// Station Wagon
//             suv : 3,// SUV
//         },
//         {
//             q : "Which of these brands is most appealing to you?",
//             o : [
//                 "BMW",
//                 "Toyota",
//                 "Mercedes-Benz",
//                 "Audi"
//             ],
//             s : 0,// Sedan
//             h : 1,// Hatchback
//             st : 2,// Station Wagon
//             suv : 3,// SUV
//         },
//         {
//             q : "How many people do you usually travel with?",
//             o : [
//                 "1-4",
//                 "1-3",
//                 "1-5",
//                 "1-6"
//             ],
//             s : 0,// Sedan
//             h : 1,// Hatchback
//             st : 2,// Station Wagon
//             suv : 3,// SUV
//         },
//         {
//             q : "How much cargo do you usually carry?",
//             o : [
//                 "Not much, but I still need space.",
//                 "Little to nothing.",
//                 "A couple of bags of supplies.",
//                 "A lot."
//             ],
//             s : 0,// Sedan
//             h : 1,// Hatchback
//             st : 2,// Station Wagon
//             suv : 3,// SUV
//         },
//         {
//             q : "What is your ideal road trip?",
//             o : [
//                 "To a city where I can show off my shiny new car to everyone",
//                 "I'm not into road trips. I'm just looking for around-town.",
//                 "To a kayaking or camping destination",
//                 "Somewhere with mountains and chilly weather"
//             ],
//             s : 0,// Sedan
//             h : 1,// Hatchback
//             st : 2,// Station Wagon
//             suv : 3,// SUV
//         }
//     ],
//
//     // (A2) HTML ELEMENTS
//     hWrap: null, // HTML quiz container
//     hQn: null, // HTML question wrapper
//     hAns: null, // HTML answers wrapper
//
//     // (A3) GAME FLAGS
//     now: 0, // current question
//     sedan: 0, // current sedans
//     hatchback: 0, // current hatchbacks
//     station: 0, // current station
//     suv: 0, // current suvs
//
//     // (B) INIT QUIZ HTML
//     init: function(){
//         // (B1) WRAPPER
//         quiz.hwrap = document.getElementById("quizWrap");
//
//         // (B2) QUESTIONS SECTION
//         quiz.hqn = document.createElement("div");
//         quiz.hqn.id = "quizQn";
//         quiz.hwrap.appendChild(quiz.hqn);
//
//         // (B3) ANSWERS SECTION
//         quiz.hans = document.createElement("div");
//         quiz.hans.id = "quizAns";
//         quiz.hwrap.appendChild(quiz.hans);
//
//         // (B4) GO!
//         quiz.draw();
//     },
//
//     // (C) DRAW QUESTION
//     draw: function(){
//         // (C1) QUESTION
//         quiz.hqn.innerHTML = quiz.data[quiz.now].q;
//
//         // (C2) OPTIONS
//         quiz.hans.innerHTML = "";
//         for (let i in quiz.data[quiz.now].o) {
//             let radio = document.createElement("input");
//             radio.type = "radio";
//             radio.name = "quiz";
//             radio.id = "quizo" + i;
//             quiz.hans.appendChild(radio);
//             let label = document.createElement("label");
//             label.innerHTML = quiz.data[quiz.now].o[i];
//             label.setAttribute("for", "quizo" + i);
//             label.dataset.idx = i;
//             label.addEventListener("click", quiz.select);
//             quiz.hans.appendChild(label);
//         }
//     },
//
//     // (D) OPTION SELECTED
//     select: function(){
//         // (D1) DETACH ALL ONCLICK
//         let all = quiz.hans.getElementsByTagName("label");
//         for (let label of all) {
//             label.removeEventListener("click", quiz.select);
//         }
//
//         // (D2) CHECK CATEGORY
//         let sedan = this.dataset.idx == quiz.data[quiz.now].s;
//         let hatchback = this.dataset.idx == quiz.data[quiz.now].h;
//         let station = this.dataset.idx == quiz.data[quiz.now].st;
//         let suv = this.dataset.idx == quiz.data[quiz.now].suv;
//
//         if (sedan) {
//             quiz.sedan++;
//             this.classList.add("sedan");
//         } else if (hatchback) {
//             quiz.hatchback++;
//             this.classList.add("hatchback");
//         }else if (station) {
//             quiz.station++;
//             this.classList.add("station");
//         }else if (suv) {
//             quiz.suv++;
//             this.classList.add("suv");
//         }
//         // (D3) NEXT QUESTION OR END GAME
//         quiz.now++;
//         setTimeout(function(){
//             if (quiz.now < quiz.data.length) { quiz.draw(); }
//             else {
//                 $.ajax({
//                     type: "POST",
//                     url: "/cars/recommend",
//                     contentType: 'text/plain',
//                     crossDomain: false,
//                     async:true
//                 });
//                 /*quiz.hqn.innerHTML = `You have answered ${quiz.score} of ${quiz.data.length} correctly.`;
//                 quiz.hans.innerHTML = "";
//                 window.location.href = "recommendedCars.html";*/
//             }
//         }, 1000);
//     },
//
//     // (E) RESTART QUIZ
//     reset : function () {
//         quiz.now = 0;
//         quiz.sedan = 0;
//         quiz.hatchback = 0;
//         quiz.station = 0;
//         quiz.suv = 0;
//         quiz.draw();
//     }
// };
// window.addEventListener("load", quiz.init);