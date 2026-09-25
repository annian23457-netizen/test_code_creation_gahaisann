package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05　呉
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
		}, "テスト05.1");
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
		}, "テスト05.2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		//		条件が満たされるまで10秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		//		機能を取得
		WebElement menuToggle = webDriver.findElement(By.className("dropdown-toggle"));
		//		機能をクリック
		menuToggle.click();
		//		"ヘルプ"が見つかるまでに10秒間待機
		WebElement helpLink = wait.until(
				ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ")));
		//		"ヘルプ"をクリック
		helpLink.click();
		//		タイトルが一致か検証
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		//　　　スクショとる
		getEvidence(new Object() {
		}, "テスト05.3");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		//		　条件が満たされるまで10秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		//  　タイトルが一致か確認
		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));

		// 　　クリック前のウィンドウを取得
		String originalWindow = webDriver.getWindowHandle();

		// 　　"よくある質問"を取得してクリック
		WebElement question = wait.until(
				ExpectedConditions.elementToBeClickable(By.linkText("よくある質問")));
		question.click();

		//　　　新しいタブが開くまで2秒待機し
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// 全ハンドルを取得し、元のウィンドウを除外する
		Set<String> handles = webDriver.getWindowHandles();
		handles.remove(originalWindow);

		// 切り替え
		webDriver.switchTo().window(handles.iterator().next());

		//  　　"よくある質問 | LMS"が見つかるまでに10秒間待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		//　　　タイトルが一致か検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// 　　　スクリーンショットを取得
		getEvidence(new Object() {
		}, "テスト05.4");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//		　条件が満たされるまで10秒間待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		// キーワード入力フォームの取得
		WebElement keyword = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("form")));
		keyword.clear();
		// キーワード入力
		keyword.sendKeys("セルフ・キャリアドック制度とは何か");

		// 確実にフォーム送信を実行
		keyword.submit();

		// 検索結果のテーブルが存在されるまで待機
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.sortabletable tbody tr")));

		// 検索結果の <dt> 内にある 2 番目の spanを取得
		WebElement questionElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector("dt.mb10 span:nth-child(2)")));

		// 検索結果を取得
		WebElement searchResultHeader = webDriver.findElement(By.xpath("//th[text()='検索結果']"));

		// ヘッダーのY座標を取得してその位置までスクロール
		String yPosition = String.valueOf(searchResultHeader.getLocation().getY());
		WebDriverUtils.scrollTo(yPosition);

		// 検索結果が一致か検証
		assertEquals("セルフ・キャリアドック制度とは何か", questionElement.getText());
		// 　　　スクリーンショットを取得
		getEvidence(new Object() {
		}, "テスト05.5");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加
		// クリアボタンの取得
		WebElement clearButton = webDriver.findElement(By.cssSelector("input.btn.btn-primary[value='クリア']"));
		// 画面トップへスクロールしてクリアボタンをクリック
		WebDriverUtils.scrollTo("0");
		clearButton.click();
		// フォーム要素の取得
		WebElement kuria = webDriver.findElement(By.id("form"));
		// 入力欄が空文字になっていることを検証
		assertEquals("", kuria.getAttribute("value"));
		//	 　　　スクリーンショットを取得
		getEvidence(new Object() {
		}, "テスト05.6");
	}

}
