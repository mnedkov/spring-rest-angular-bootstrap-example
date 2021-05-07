package ai.automationlabs.exchangerates.domain.currencylayer;

public class BaseResponse {
	private boolean success;
	private Error error;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public static class Error {
		private int code;
		private String info;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}

		@Override
		public String toString() {
			return "Error [code=" + code + ", info=" + info + "]";
		}

	}

}
