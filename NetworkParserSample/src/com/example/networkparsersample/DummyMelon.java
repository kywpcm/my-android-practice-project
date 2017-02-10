package com.example.networkparsersample;

/* Open API를 통해 얻어 온 json 컨텐츠
Date: Mon, 17 Jun 2013 05:02:43 GMT
Server: Apache
Content-Language: ko,ko-kr
Content-Length: 3336
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: x-requested-with, x-requested-by,  Content-Type, appKey
Connection: close
Content-Type: application/json;charset=UTF-8
Status code: 200 OK

{
    "melon":{
        "menuId":54020101,
        "count":10,
        "page":1,
        "totalPages":10,
        "rankDay":"20130617",
        "rankHour":"13",
        "songs":{
            "song":[
                {
                    "songId":4138606,
                    "songName":"Give It To Me",
                    "artists":{
                        "artist":[
                            {
                                "artistId":473181,
                                "artistName":"씨스타"
                            }
                        ]
                    },
                    "albumId":2188413,
                    "albumName":"Give It To Me",
                    "currentRank":1,
                    "pastRank":1,
                    "playTime":201,
                    "issueDate":"20130611",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4138607,
                    "songName":"넌 너무 야해 (Feat. 긱스) (The Way You Make Me Melt)",
                    "artists":{
                        "artist":[
                            {
                                "artistId":473181,
                                "artistName":"씨스타"
                            }
                        ]
                    },
                    "albumId":2188413,
                    "albumName":"Give It To Me",
                    "currentRank":2,
                    "pastRank":2,
                    "playTime":215,
                    "issueDate":"20130611",
                    "isTitleSong":"false",
                    "isHitSong":"true",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4129672,
                    "songName":"짧은머리",
                    "artists":{
                        "artist":[
                            {
                                "artistId":28801,
                                "artistName":"허각"
                            },
                            {
                                "artistId":644871,
                                "artistName":"정은지 (에이핑크)"
                            }
                        ]
                    },
                    "albumId":2187275,
                    "albumName":"`A Cube` For Season # Blue",
                    "currentRank":3,
                    "pastRank":3,
                    "playTime":220,
                    "issueDate":"20130531",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4130922,
                    "songName":"미친연애 (Bad Girl) (Feat. E-Sens Of 슈프림팀)",
                    "artists":{
                        "artist":[
                            {
                                "artistId":623851,
                                "artistName":"범키"
                            }
                        ]
                    },
                    "albumId":2187466,
                    "albumName":"미친연애",
                    "currentRank":4,
                    "pastRank":5,
                    "playTime":257,
                    "issueDate":"20130603",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4141700,
                    "songName":"첫사랑",
                    "artists":{
                        "artist":[
                            {
                                "artistId":322666,
                                "artistName":"애프터스쿨"
                            }
                        ]
                    },
                    "albumId":2188765,
                    "albumName":"After School The 6th Maxi Single `첫사랑`",
                    "currentRank":5,
                    "pastRank":4,
                    "playTime":217,
                    "issueDate":"20130613",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4138608,
                    "songName":"바빠 (Bad Boy)",
                    "artists":{
                        "artist":[
                            {
                                "artistId":473181,
                                "artistName":"씨스타"
                            }
                        ]
                    },
                    "albumId":2188413,
                    "albumName":"Give It To Me",
                    "currentRank":6,
                    "pastRank":6,
                    "playTime":211,
                    "issueDate":"20130611",
                    "isTitleSong":"false",
                    "isHitSong":"true",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4132144,
                    "songName":"너 하나야",
                    "artists":{
                        "artist":[
                            {
                                "artistId":100176,
                                "artistName":"포맨"
                            }
                        ]
                    },
                    "albumId":2187663,
                    "albumName":"구가의 서 OST Part.7",
                    "currentRank":7,
                    "pastRank":8,
                    "playTime":288,
                    "issueDate":"20130604",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4144408,
                    "songName":"All Right",
                    "artists":{
                        "artist":[
                            {
                                "artistId":643851,
                                "artistName":"김예림 (투개월)"
                            }
                        ]
                    },
                    "albumId":2187662,
                    "albumName":"A Voice",
                    "currentRank":8,
                    "pastRank":0,
                    "playTime":208,
                    "issueDate":"20130617",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4144135,
                    "songName":"I`m Sorry",
                    "artists":{
                        "artist":[
                            {
                                "artistId":436981,
                                "artistName":"비스트"
                            }
                        ]
                    },
                    "albumId":2189104,
                    "albumName":"I`m Sorry",
                    "currentRank":9,
                    "pastRank":7,
                    "playTime":228,
                    "issueDate":"20130615",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                },
                {
                    "songId":4144539,
                    "songName":"A Good Boy",
                    "artists":{
                        "artist":[
                            {
                                "artistId":646173,
                                "artistName":"백아연"
                            }
                        ]
                    },
                    "albumId":2189139,
                    "albumName":"A Good Girl",
                    "currentRank":10,
                    "pastRank":0,
                    "playTime":230,
                    "issueDate":"20130617",
                    "isTitleSong":"true",
                    "isHitSong":"false",
                    "isAdult":"false",
                    "isFree":"false"
                }
            ]
        }
    }
}*/

//json 파일의 최상위 객체({})
public class DummyMelon {
	//파싱할 json 컨텐츠에서 최상위 객체 밑의 하위 객체들
	//GsonResultParser<T> parser를 사용한 json 파싱은 json 컨텐츠의 객체 구조만 맞으면 파싱 됨..
	Melon melon;
}
