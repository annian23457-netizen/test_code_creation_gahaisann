package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
		}, "テスト08.1");
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
		}, "テスト08.2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 提出済みの行にある「詳細」ボタン一覧を取得
		List<WebElement> detailButtons = webDriver.findElements(
				By.xpath("//tr[td/span[text()='提出済み']]//input[@value='詳細']"));

		// 2番目の「詳細」ボタンを取得
		WebElement detailButton = detailButtons.get(1);

		// JavaScriptで物理クリックの干渉を無視して直接クリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailButton);

		// タイトルが一致か検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト08.3");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 画面をスクロール
		scrollBy("400");

		// 「提出済み週報【デモ】を確認する」ボタンを取得してクリック
		webDriver.findElement(By.cssSelector("input[value*='提出済み週報【デモ】を確認する']")).click();

		// 送信ボタンが表示されるまで待機
		visibilityTimeout(By.cssSelector("button[type='submit']"), 5);

		// タイトルが一致か検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト08.4");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容の入力エリアを取得
		WebElement inputText = webDriver.findElement(By.id("content_1"));

		// 入力エリアをクリア
		inputText.clear();

		// 修正後の報告内容を入力
		inputText.sendKeys("報告内容を修正しました。");

		// 「提出する」ボタンを取得
		WebElement submitbutton = webDriver.findElement(By.cssSelector("button[type='submit']"));

		// 画面をスクロール
		scrollBy("400");

		// ボタンをクリック
		submitbutton.click();

		// タイトルが一致か検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト08.5");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// 「ようこそ」を含むリンク要素を取得
		WebElement welcomElement = webDriver.findElement(By.partialLinkText("ようこそ"));

		// リンクをクリック
		welcomElement.click();

		// タイトルが一致か検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		// スクショとる
		getEvidence(new Object() {
		}, "テスト08.6");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 画面をスクロール
		scrollBy("400");

		// 明示的待機オブジェクトの生成（最大10秒）
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 週報のレポート行にある「詳細」ボタンのロケータ定義
		By detailButtonLocator = By.xpath(
				"//h3[text()='レポート']/following-sibling::table//tr[td[contains(text(),'週報')]]//form[contains(@action,'/report/detail')]//input[@value='詳細']");

		// 要素が存在・表示されるまで待機して取得
		WebElement detailButton = wait.until(ExpectedConditions.presenceOfElementLocated(detailButtonLocator));

		// JavaScriptで確実にクリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailButton);

		// 画面をスクロール
		scrollBy("400");

		// 所感項目の値を取得してテキストを検証
		By impressionTd = By.xpath("//th[contains(text(),'所感')]/following-sibling::td");
		String actualImpressionTd = webDriver.findElement(impressionTd).getText().trim();
		assertEquals("報告内容を修正しました。", actualImpressionTd);

		// スクショとる
		getEvidence(new Object() {
		}, "テスト08.7");
	}
}