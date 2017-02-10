package com.example.networkparsersample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

/* Open API를 통해 얻어 온 xml 컨텐츠
Date: Mon, 17 Jun 2013 05:14:35 GMT
Server: Apache
Content-Language: ko,ko-kr
Content-Length: 4821
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: x-requested-with, x-requested-by,  Content-Type, appKey
Connection: close
Content-Type: application/xml;charset=UTF-8
Status code: 200 OK

<?xml version="1.0" encoding="UTF-8"?>

<melon>
    <menuId>54020101</menuId>
    <count>10</count>
    <page>1</page>
    <totalPages>10</totalPages>
    <rankDay>20130617</rankDay>
    <rankHour>14</rankHour>
    <songs>
        <song>
            <songId>4138606</songId>
            <songName>Give It To Me</songName>
            <artists>
                <artist>
                    <artistId>473181</artistId>
                    <artistName>씨스타</artistName>
                </artist>
            </artists>
            <albumId>2188413</albumId>
            <albumName>Give It To Me</albumName>
            <currentRank>1</currentRank>
            <pastRank>1</pastRank>
            <playTime>201</playTime>
            <issueDate>20130611</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4138607</songId>
            <songName>넌 너무 야해 (Feat. 긱스) (The Way You Make Me Melt)</songName>
            <artists>
                <artist>
                    <artistId>473181</artistId>
                    <artistName>씨스타</artistName>
                </artist>
            </artists>
            <albumId>2188413</albumId>
            <albumName>Give It To Me</albumName>
            <currentRank>2</currentRank>
            <pastRank>2</pastRank>
            <playTime>215</playTime>
            <issueDate>20130611</issueDate>
            <isTitleSong>false</isTitleSong>
            <isHitSong>true</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4129672</songId>
            <songName>짧은머리</songName>
            <artists>
                <artist>
                    <artistId>28801</artistId>
                    <artistName>허각</artistName>
                </artist>
                <artist>
                    <artistId>644871</artistId>
                    <artistName>정은지 (에이핑크)</artistName>
                </artist>
            </artists>
            <albumId>2187275</albumId>
            <albumName>`A Cube` For Season # Blue</albumName>
            <currentRank>3</currentRank>
            <pastRank>3</pastRank>
            <playTime>220</playTime>
            <issueDate>20130531</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4144408</songId>
            <songName>All Right</songName>
            <artists>
                <artist>
                    <artistId>643851</artistId>
                    <artistName>김예림 (투개월)</artistName>
                </artist>
            </artists>
            <albumId>2187662</albumId>
            <albumName>A Voice</albumName>
            <currentRank>4</currentRank>
            <pastRank>8</pastRank>
            <playTime>208</playTime>
            <issueDate>20130617</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4130922</songId>
            <songName>미친연애 (Bad Girl) (Feat. E-Sens Of 슈프림팀)</songName>
            <artists>
                <artist>
                    <artistId>623851</artistId>
                    <artistName>범키</artistName>
                </artist>
            </artists>
            <albumId>2187466</albumId>
            <albumName>미친연애</albumName>
            <currentRank>5</currentRank>
            <pastRank>4</pastRank>
            <playTime>257</playTime>
            <issueDate>20130603</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4138608</songId>
            <songName>바빠 (Bad Boy)</songName>
            <artists>
                <artist>
                    <artistId>473181</artistId>
                    <artistName>씨스타</artistName>
                </artist>
            </artists>
            <albumId>2188413</albumId>
            <albumName>Give It To Me</albumName>
            <currentRank>6</currentRank>
            <pastRank>6</pastRank>
            <playTime>211</playTime>
            <issueDate>20130611</issueDate>
            <isTitleSong>false</isTitleSong>
            <isHitSong>true</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4132144</songId>
            <songName>너 하나야</songName>
            <artists>
                <artist>
                    <artistId>100176</artistId>
                    <artistName>포맨</artistName>
                </artist>
            </artists>
            <albumId>2187663</albumId>
            <albumName>구가의 서 OST Part.7</albumName>
            <currentRank>7</currentRank>
            <pastRank>7</pastRank>
            <playTime>288</playTime>
            <issueDate>20130604</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4141700</songId>
            <songName>첫사랑</songName>
            <artists>
                <artist>
                    <artistId>322666</artistId>
                    <artistName>애프터스쿨</artistName>
                </artist>
            </artists>
            <albumId>2188765</albumId>
            <albumName>After School The 6th Maxi Single `첫사랑`</albumName>
            <currentRank>8</currentRank>
            <pastRank>5</pastRank>
            <playTime>217</playTime>
            <issueDate>20130613</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4144539</songId>
            <songName>A Good Boy</songName>
            <artists>
                <artist>
                    <artistId>646173</artistId>
                    <artistName>백아연</artistName>
                </artist>
            </artists>
            <albumId>2189139</albumId>
            <albumName>A Good Girl</albumName>
            <currentRank>9</currentRank>
            <pastRank>10</pastRank>
            <playTime>230</playTime>
            <issueDate>20130617</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
        <song>
            <songId>4144135</songId>
            <songName>I`m Sorry</songName>
            <artists>
                <artist>
                    <artistId>436981</artistId>
                    <artistName>비스트</artistName>
                </artist>
            </artists>
            <albumId>2189104</albumId>
            <albumName>I`m Sorry</albumName>
            <currentRank>10</currentRank>
            <pastRank>9</pastRank>
            <playTime>228</playTime>
            <issueDate>20130615</issueDate>
            <isTitleSong>true</isTitleSong>
            <isHitSong>false</isHitSong>
            <isAdult>false</isAdult>
            <isFree>false</isFree>
        </song>
    </songs>
</melon>*/

//SaxParserHandler를 상속한 클래스
public class Melon implements SaxParserHandler {
	
	private static final String TAG = "Melon";
	
	//파싱 할 xml 컨텐츠에서 melon 태그 밑의 하위 태그들
	int menuId;
	String rankDay;
	Songs songs;
	
	public Melon() {
		Log.i(TAG, "Melon() 생성자");
	}
	
	//inherited abstract method 구현..
	//GsonResultParser<T> parser를 사용한 json 파싱은 밑의 SaxParserHandler 메소드 구현 같은 거 필요없음..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() 메소드 시작");
		Log.d(TAG, "get tag name is melon..!");
		return "melon";  //처음으로 찾는 태그 네임은 "melon"이다.
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("songs")) {  //start 태그 네임이 "songs"와 일치하면,
			Log.d(TAG, "start tag name is songs..!");
			Songs songs = new Songs();  //Songs 타입 클래스의 songs 객체에게
			parser.pushHandler(songs);  //파싱을 push한다. 할 일을 위임한다.
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() 메소드 시작");
		if (tagName.equalsIgnoreCase("menuId")) {  //end 태그 네임이 "songs"와 일치하면,
			Log.d(TAG, "end tag name is menuId..!");
			this.menuId = Integer.parseInt((String)content);  //content를 가져온다.
		} else if (tagName.equalsIgnoreCase("rankDay")) {
			Log.d(TAG, "end tag name is rankDay..!");
			this.rankDay = (String)content;
		} else if (tagName.equalsIgnoreCase("songs")) {  //end 태그 네임이 "songs"와 일치하면,
			Log.d(TAG, "end tag name is songs..!");
			songs = (Songs)content;  //content를 가져온다.
		}
		
	}
	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() 메소드 시작, return this");
		return this;
	}
}
