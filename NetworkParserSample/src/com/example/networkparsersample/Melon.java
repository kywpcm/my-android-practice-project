package com.example.networkparsersample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;

import com.example.networkparsersample.parser.SaxParserHandler;
import com.example.networkparsersample.parser.SaxResultParser;

/* Open API�� ���� ��� �� xml ������
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
                    <artistName>����Ÿ</artistName>
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
            <songName>�� �ʹ� ���� (Feat. �㽺) (The Way You Make Me Melt)</songName>
            <artists>
                <artist>
                    <artistId>473181</artistId>
                    <artistName>����Ÿ</artistName>
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
            <songName>ª���Ӹ�</songName>
            <artists>
                <artist>
                    <artistId>28801</artistId>
                    <artistName>�㰢</artistName>
                </artist>
                <artist>
                    <artistId>644871</artistId>
                    <artistName>������ (������ũ)</artistName>
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
                    <artistName>�迹�� (������)</artistName>
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
            <songName>��ģ���� (Bad Girl) (Feat. E-Sens Of ��������)</songName>
            <artists>
                <artist>
                    <artistId>623851</artistId>
                    <artistName>��Ű</artistName>
                </artist>
            </artists>
            <albumId>2187466</albumId>
            <albumName>��ģ����</albumName>
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
            <songName>�ٺ� (Bad Boy)</songName>
            <artists>
                <artist>
                    <artistId>473181</artistId>
                    <artistName>����Ÿ</artistName>
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
            <songName>�� �ϳ���</songName>
            <artists>
                <artist>
                    <artistId>100176</artistId>
                    <artistName>����</artistName>
                </artist>
            </artists>
            <albumId>2187663</albumId>
            <albumName>������ �� OST Part.7</albumName>
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
            <songName>ù���</songName>
            <artists>
                <artist>
                    <artistId>322666</artistId>
                    <artistName>�����ͽ���</artistName>
                </artist>
            </artists>
            <albumId>2188765</albumId>
            <albumName>After School The 6th Maxi Single `ù���`</albumName>
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
                    <artistName>��ƿ�</artistName>
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
                    <artistName>��Ʈ</artistName>
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

//SaxParserHandler�� ����� Ŭ����
public class Melon implements SaxParserHandler {
	
	private static final String TAG = "Melon";
	
	//�Ľ� �� xml ���������� melon �±� ���� ���� �±׵�
	int menuId;
	String rankDay;
	Songs songs;
	
	public Melon() {
		Log.i(TAG, "Melon() ������");
	}
	
	//inherited abstract method ����..
	//GsonResultParser<T> parser�� ����� json �Ľ��� ���� SaxParserHandler �޼ҵ� ���� ���� �� �ʿ����..
	@Override
	public String getTagName() {
		Log.e(TAG, "getTagName() �޼ҵ� ����");
		Log.d(TAG, "get tag name is melon..!");
		return "melon";  //ó������ ã�� �±� ������ "melon"�̴�.
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseStartElement() �޼ҵ� ����");
		if (tagName.equalsIgnoreCase("songs")) {  //start �±� ������ "songs"�� ��ġ�ϸ�,
			Log.d(TAG, "start tag name is songs..!");
			Songs songs = new Songs();  //Songs Ÿ�� Ŭ������ songs ��ü����
			parser.pushHandler(songs);  //�Ľ��� push�Ѵ�. �� ���� �����Ѵ�.
		}
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		Log.e(TAG, "parseEndElement() �޼ҵ� ����");
		if (tagName.equalsIgnoreCase("menuId")) {  //end �±� ������ "songs"�� ��ġ�ϸ�,
			Log.d(TAG, "end tag name is menuId..!");
			this.menuId = Integer.parseInt((String)content);  //content�� �����´�.
		} else if (tagName.equalsIgnoreCase("rankDay")) {
			Log.d(TAG, "end tag name is rankDay..!");
			this.rankDay = (String)content;
		} else if (tagName.equalsIgnoreCase("songs")) {  //end �±� ������ "songs"�� ��ġ�ϸ�,
			Log.d(TAG, "end tag name is songs..!");
			songs = (Songs)content;  //content�� �����´�.
		}
		
	}
	@Override
	public Object getParseResult() {
		Log.e(TAG, "getParseResult() �޼ҵ� ����, return this");
		return this;
	}
}
