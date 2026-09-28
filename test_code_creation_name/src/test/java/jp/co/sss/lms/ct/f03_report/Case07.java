package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//		URLを取得
		webDriver.get("http://localhost:8080/lms/");
		//		タイトルが一致か検証
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト07.1");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//		ログインIDを取得
		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		//　　　ログインIDをクリック
		loginIdInput.click();
		//　　　ログインIDの値を入力
		loginIdInput.sendKeys("StudentAA01");

		//		パスワードを取得
		WebElement passwordInput = webDriver.findElement(By.id("password"));
		//		パスワードをクリック
		passwordInput.clear();
		//		パスワードの値を入力
		passwordInput.sendKeys("Aa123456789");
		//　　　ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		//		タイトルが一致か検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト07.2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		//未提出の文字列を含む行の中にある「詳細」ボタンを取得してクリック
		WebElement detailButton = webDriver.findElement(
				By.xpath("//tr[td/span[text()='未提出']]//input[@value='詳細']"));

		// JavaScriptで物理クリックの干渉を無視して直接クリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailButton);
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		}, "テスト07.3");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加

		WebElement passwordInput = webDriver.findElement(By.cssSelector("input[value*='を提出する']"));
		passwordInput.click();
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		}, "テスト07.4");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加
		WebElement inputText = webDriver.findElement(By.id("content_0"));
		inputText.clear();
		inputText.sendKeys("本日の研修内容です。");
		WebElement submitbutton = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitbutton.click();
		boolean evidenceConfirmButton = webDriver.findElement(By.cssSelector("input[value*='提出済み']")).isDisplayed();
		assertTrue(evidenceConfirmButton);
		getEvidence(new Object() {
		}, "テスト07.5");
	}

}
